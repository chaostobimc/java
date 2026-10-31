import sys, os, re, json
from jawa.classloader import ClassLoader
from jawa.constants import (MethodReference, InterfaceMethodRef, FieldReference, ConstantClass, UTF8,
                            String as JString, Integer, Float, Long, Double, InvokeDynamic)
from jawa.cf import ClassFile

def cname(c):
    return c.value if hasattr(c,'value') else str(c)

class Fmt:
    def __init__(self, cf):
        self.cf = cf
        self.cp = cf.constants
    def indy_recipe(self, c):
        """Return a printable string-concat expression or None."""
        try:
            bm = self.cf.bootstrap_methods[c.bootstrap_method_attr_index]
        except Exception:
            return None
        args = list(bm.bootstrap_args)
        try:
            ref = self.cp.get(bm.method_ref)
            if hasattr(ref, 'reference'):
                ref = ref.reference
            owner = cname(ref.class_.name)
            mname = cname(ref.name_and_type.name)
        except Exception:
            return None
        if 'StringConcatFactory' in owner and mname.startswith('makeConcat'):
            if not args: return None
            try:
                recipe = self.cp.get(args[0]).string.value
            except Exception:
                return None
            consts = []
            for a in args[1:]:
                try:
                    cc = self.cp.get(a)
                    consts.append(repr(cc.string.value) if hasattr(cc,'string') else repr(cc.value))
                except Exception:
                    consts.append('?')
            recipe = recipe.replace('\\x01','\x01').replace('\\x02','\x02')
            parts=[]; buf=''; ci=0
            for ch in recipe:
                if ch == '\x01':
                    if buf: parts.append(repr(buf)); buf=''
                    parts.append('{}')
                elif ch == '\x02':
                    if buf: parts.append(repr(buf)); buf=''
                    parts.append(consts[ci] if ci < len(consts) else '?'); ci += 1
                else:
                    buf += ch
            if buf: parts.append(repr(buf))
            return parts
        return None

    def get(self, idx):
        try: return self.cp.get(idx)
        except Exception: return None
    def const(self, c):
        if isinstance(c, (MethodReference, InterfaceMethodRef)):
            cls = cname(c.class_.name)
            nm = cname(c.name_and_type.name); d = cname(c.name_and_type.descriptor)
            return ('method', cls, nm, d)
        if isinstance(c, FieldReference):
            return ('field', cname(c.class_.name), cname(c.name_and_type.name), cname(c.name_and_type.descriptor))
        if isinstance(c, ConstantClass): return ('class', cname(c.name))
        if isinstance(c, JString): return ('str', c.string.value)
        if isinstance(c, UTF8): return ('utf8', c.value)
        if isinstance(c, InvokeDynamic):
            nm = cname(c.name_and_type.name); d = cname(c.name_and_type.descriptor)
            return ('indy', nm, d)
        if isinstance(c, Integer): return ('int', c.value)
        if isinstance(c, Float): return ('float', c.value)
        if isinstance(c, Long): return ('long', c.value)
        if isinstance(c, Double): return ('double', c.value)
        return ('other', str(c))

PREFIXES = ['net/friendswithmoney/fwm/','net/minecraft/','net/fabricmc/','com/google/gson/',
            'com/mojang/','org/spongepowered/','java/lang/','java/util/','java/nio/file/','java/nio/charset/',
            'java/io/','java/net/','java/awt/','java/time/','java/math/','java/util/concurrent/','java/util/regex/',
            'java/util/zip/','javax/imageio/','org/w3c/dom/']

def simp(s):
    for p in PREFIXES:
        if s.startswith(p):
            s = s[len(p):]
            break
    return s.replace('/', '.')

def parse_desc(d):
    # (args)ret
    i = d.index(')')
    args = d[1:i]; ret = d[i+1:]
    return args, ret

def split_args(args):
    out=[]; i=0
    while i < len(args):
        j=i
        while args[j]=='[': j+=1
        if args[j]=='L':
            j = args.index(';', j)
        out.append(args[i:j+1]); i=j+1
    return out

PRIM = {'Z':'boolean','B':'byte','C':'char','S':'short','I':'int','J':'long','F':'float','D':'double','V':'void'}
def short_type(t):
    if t.startswith('['): return short_type(t[1:])+'[]'
    if t.startswith('L'): return simp(t[1:-1])
    return PRIM.get(t,t)

SENT = re.compile('\x00(\\d+)\x00')

class Decompiler:
    def __init__(self, cf):
        self.cf = cf
        self.fmt = Fmt(cf)
        self.subs = {}
    def resolve(self, s, depth=0):
        if depth>8: return s
        def rep(m):
            k=int(m.group(1))
            return self.resolve(self.subs.get(k,'?'), depth+1)
        return SENT.sub(rep, s)

    def render_method(self, m):
        out=[]
        name = cname(m.name); desc = cname(m.descriptor)
        if not m.code:
            return f"// {name}{simp(desc)}  (abstract/native)\n"
        args, ret = parse_desc(desc)
        at = split_args(args)
        sl = 0
        lvt = {}
        for a in m.code.attributes:
            if a.name.value == 'LocalVariableTable':
                for e in a.local_variables:
                    lvt.setdefault(e.index, (cname(self.cf.constants.get(e.name_index)), cname(self.cf.constants.get(e.descriptor_index))))

        # locals naming
        try:
            is_static = bool(m.access_flags.get('acc_static'))
        except Exception:
            is_static = False
        idx = 0
        if not is_static:
            idx = 1
        locals_ = {}
        locals_[0] = ('this','Lself;') if not is_static else None
        for t in at:
            nm = lvt[idx][0] if idx in lvt else f"a{idx}"
            locals_[idx] = (nm, t); idx += 1
        if ret != 'V': pass
        instrs = list(m.code.disassemble())
        handlers = {}
        for e in (m.code.exception_table or []):
            handlers.setdefault(e.handler_pc, []).append(e)
        # labels
        targets=set()
        for ins in instrs:
            for o in ins.operands:
                if o.op_type.name=='BRANCH':
                    targets.add(ins.pos+o.value)
        st=[]; lines=[]
        jump_target_before = {}
        def push(x): st.append(x)
        def pop():
            return st.pop() if st else '?'
        def p():  # peek
            return st[-1] if st else '?'
        def emit(s): lines.append(s)
        def bref(t): return f"L{t}"
        def finalize():
            for i,l in enumerate(lines):
                lines[i]=self.resolve(l)

        for k,ins in enumerate(instrs):
            if ins.pos in targets:
                emit(f"L{ins.pos}:")
            if ins.pos in handlers:
                try:
                    ct = handlers[ins.pos][0].catch_type
                    tn = 'any' if not ct else cname(self.cf.constants.get(ct).name)
                except Exception:
                    tn = 'any'
                st = [f"/*catch {simp(tn)}*/ ex"]
            mn = ins.mnemonic
            ops = ins.operands
            def cidx(n=0):
                return self.fmt.get(ops[n].value)
            if mn=='nop': continue
            if mn.startswith('aconst_null'): push('null'); continue
            if mn in ('iconst_m1','iconst_0','iconst_1','iconst_2','iconst_3','iconst_4','iconst_5'):
                push(mn.split('_')[1]); continue
            if mn in ('lconst_0','lconst_1','fconst_0','fconst_1','fconst_2','dconst_0','dconst_1'):
                push(mn.split('_')[1]); continue
            if mn in ('bipush','sipush'): push(str(ops[0].value)); continue
            if mn in ('ldc','ldc_w','ldc2_w'):
                c=self.fmt.const(cidx())
                if c[0]=='str': push(json.dumps(c[1]))
                elif c[0] in ('int','float','long','double'): push(str(c[1]))
                elif c[0]=='class': push(f"{short_type('L'+c[1]+';')}.class")
                elif c[0]=='method': push(f"/*{c[1]}.{c[2]}*/")
                else: push(f"/*ldc {c}*/")
                continue
            if re.fullmatch(r'[ailfd]load|[ailfd]load_\d', mn):
                if '_' in mn: i_ = int(mn.split('_')[1])
                else: i_ = int(ops[0].value)
                nm = locals_.get(i_,(f'v{i_}',None))[0]
                push(nm); continue
            if re.fullmatch(r'[ailfd]store|[ailfd]store_\d', mn):
                if '_' in mn: i_=int(mn.split('_')[1])
                else: i_=int(ops[0].value)
                v=pop()
                nm = locals_.get(i_,(f'v{i_}',None))
                if nm is None or nm[0] is None:
                    locals_[i_]=(f'v{i_}',None); nm=locals_[i_]
                emit(f"{nm[0]} = {v};"); continue
            if mn in ('iaload','laload','faload','daload','aaload','baload','caload','saload'):
                i=pop(); a=pop(); push(f"{a}[{i}]"); continue
            if mn in ('iastore','lastore','fastore','dastore','aastore','bastore','castore','sastore'):
                v=pop(); i=pop(); a=pop(); emit(f"{a}[{i}] = {v};"); continue
            if mn=='pop':
                v=pop()
                if isinstance(v,str) and '(' in v and not v.startswith('('):
                    emit(v+';')
                continue
            if mn=='pop2': pop(); pop(); continue
            if mn=='dup': push(p()); continue
            if mn=='dup_x1':
                a=pop(); b=pop(); push(a); push(b); push(a); continue
            if mn=='dup_x2':
                a=pop(); b=pop(); c=pop(); push(a); push(c); push(b); push(a); continue
            if mn=='dup2':
                a=pop(); b=p(); push(a); push(b); push(a); continue
            if mn=='swap':
                a=pop(); b=pop(); push(a); push(b); continue
            if mn in ('iadd','ladd','fadd','dadd','isub','lsub','fsub','dsub','imul','lmul','fmul','dmul','idiv','ldiv','fdiv','ddiv','irem','lrem','frem','drem'):
                b=pop(); a=pop(); op={'i':'','l':'','f':'','d':''}
                sym={'add':'+','sub':'-','mul':'*','div':'/','rem':'%'}[re.sub(r'^[ilfd]','',mn)]
                push(f"({a} {sym} {b})"); continue
            if mn in ('ineg','lneg','fneg','dneg'):
                push(f"(-{pop()})"); continue
            if mn in ('ishl','lshl','ishr','lshr','iushr','lushr','iand','land','ior','lor','ixor','lxor'):
                b=pop(); a=pop()
                sym={'shl':'<<','shr':'>>','ushr':'>>>','and':'&','or':'|','xor':'^'}[re.sub(r'^[il]','',mn)]
                push(f"({a} {sym} {b})"); continue
            if mn in ('i2l','i2f','i2d','l2i','l2f','l2d','f2i','f2l','f2d','d2i','d2l','d2f','i2b','i2c','i2s'):
                push(f"(({mn.split('2')[1]}){pop()})"); continue
            if mn in ('lcmp','fcmpl','fcmpg','dcmpl','dcmpg'):
                b=pop(); a=pop(); push(f"cmp({a},{b})"); continue
            if mn in ('ifeq','ifne','iflt','ifge','ifgt','ifle'):
                a=pop(); sym={'eq':'==0','ne':'!=0','lt':'<0','ge':'>=0','gt':'>0','le':'<=0'}[mn[2:]]
                emit(f"if ({a} {sym}) goto {bref(ins.pos+ops[0].value)};"); continue
            if mn in ('if_icmpeq','if_icmpne','if_icmplt','if_icmpge','if_icmpgt','if_icmple','if_acmpeq','if_acmpne'):
                b=pop(); a=pop()
                s=mn.split('cmp')[1]; sym={'eq':'==','ne':'!=','lt':'<','ge':'>=','gt':'>','le':'<='}[s]
                emit(f"if ({a} {sym} {b}) goto {bref(ins.pos+ops[0].value)};"); continue
            if mn in ('ifnull','ifnonnull'):
                a=pop(); sym = '== null' if mn=='ifnull' else '!= null'
                emit(f"if ({a} {sym}) goto {bref(ins.pos+ops[0].value)};"); continue
            if mn=='goto': emit(f"goto {bref(ins.pos+ops[0].value)};"); continue
            if mn=='goto_w': emit(f"goto {bref(ins.pos+ops[0].value)};"); continue
            if mn.startswith('tableswitch') or mn.startswith('lookupswitch'):
                emit(f"// switch ({pop()}) -> {ops}"); continue
            if mn=='ireturn': emit(f"return {pop()};"); continue
            if mn in ('lreturn','freturn','dreturn','areturn'): emit(f"return {pop()};"); continue
            if mn=='return': emit("return;"); continue
            if mn=='athrow': emit(f"throw {pop()};"); continue
            if mn=='checkcast':
                c=self.fmt.const(cidx()); t=short_type('L'+c[1]+';') if c[0]=='class' else c[1]
                push(f"(({t}){pop()})"); continue
            if mn=='instanceof':
                c=self.fmt.const(cidx()); t=short_type('L'+c[1]+';') if c[0]=='class' else c[1]
                push(f"({pop()} instanceof {t})"); continue
            if mn=='anewarray':
                c=self.fmt.const(cidx()); t = c[1] if c[0]=='class' else '?'
                push(f"new {short_type('L'+t+';')}[{pop()}]"); continue
            if mn=='newarray':
                tmap={4:'boolean',5:'char',6:'float',7:'double',8:'byte',9:'short',10:'int',11:'long'}
                push(f"new {tmap.get(ops[0].value,'?')}[{pop()}]"); continue
            if mn=='multianewarray':
                c=self.fmt.const(cidx()); d=ops[1].value
                dims=[pop() for _ in range(d)][::-1]
                push(f"new {c[1]}"+"".join(f"[{x}]" for x in dims)); continue
            if mn=='arraylength':
                push(f"{pop()}.length"); continue
            if mn=='new':
                c=self.fmt.const(cidx())
                tok='\x00%d\x00'%len(self.subs); self.subs[str(len(self.subs))]=f"new {short_type('L'+c[1]+';')}()"
                push(tok); continue
            if mn in ('getstatic','getfield'):
                c=self.fmt.const(cidx())
                fld = f"{simp(c[1])}.{c[2]}"
                if mn=='getfield':
                    obj=pop(); push(f"{obj}.{c[2]}")
                else: push(fld)
                continue
            if mn in ('putstatic','putfield'):
                c=self.fmt.const(cidx())
                v=pop()
                if mn=='putfield':
                    obj=pop(); emit(f"{obj}.{c[2]} = {v};")
                else: emit(f"{simp(c[1])}.{c[2]} = {v};")
                continue
            if mn.startswith('invoke'):
                c=self.fmt.const(cidx())
                if c[0]=='indy':
                    args,ret = parse_desc(c[2]); at2=split_args(args)
                    aa=[pop() for _ in at2][::-1]
                    parts = None
                    try:
                        parts = self.fmt.indy_recipe(cidx())
                    except Exception:
                        parts = None
                    if parts is not None and 'StringConcatFactory' in '':
                        pass
                    if parts is not None and c[1].startswith('makeConcat'):
                        it = iter(aa); out=[]
                        for _part in parts:
                            out.append(next(it, '?') if _part == '{}' else _part)
                        call=' + '.join(out)
                    else:
                        call=f"/*indy*/{c[1]}({', '.join(aa)})"
                else:
                    _,cls,nm,d = c
                    args,ret=parse_desc(d); at2=split_args(args)
                    aa=[pop() for _ in at2][::-1]
                    if mn=='invokestatic':
                        call=f"{simp(cls)}.{nm}({', '.join(aa)})"
                    else:
                        obj=pop()
                        if nm=='<init>':
                            self.subs[str(len(self.subs))]=f"{obj}"; # placeholder unused
                            newtok='\x00%d\x00'%(len(self.subs)-1)
                            # rewrite placeholder to real ctor call
                            self.subs[str(len(self.subs)-1)]=f"new {simp(cls)}({', '.join(aa)})"
                            # replace obj token occurrence in stack
                            for i,x in enumerate(st):
                                if x==obj: st[i]=newtok
                            call=newtok
                            emit(f"{obj} = {newtok};") if False else None
                            # nothing else; constructor result not pushed
                            continue
                        elif nm=='<clinit>':
                            continue
                        else:
                            call=f"{obj}.{nm}({', '.join(aa)})"
                if ret=='V': emit(f"{call};")
                else: push(call)
                continue
            if mn=='iinc':
                i_=int(ops[0].value); c_=int(ops[1].value)
                nm=locals_.get(i_,(f'v{i_}',None))[0]
                emit(f"{nm} = {nm} + {c_};"); continue
            if mn in ('monitorenter','monitorexit'):
                emit(f"// {mn} {pop()}"); continue
            if mn in ('jsr','ret'): emit(f"// {mn}"); continue
            if mn=='breakpoint' or mn=='impdep1' or mn=='impdep2': continue
            emit(f"// ? {mn} {[ (o.op_type.name,o.value) for o in ops]}")
        finalize()
        sig_args = ', '.join(f"{short_type(t)} {locals_.get(i,(f'a{i}',)) [0]}" for i,t in enumerate(at, start=(0 if is_static else 1)))
        params=''
        # rebuild param list from locals
        pl=[]
        start = 0 if is_static else 1
        for j,t in enumerate(at):
            nm = locals_.get(start+j, (f'a{start+j}',))[0]
            pl.append(f"{short_type(t)} {nm}")
        rets = 'void' if ret=='V' else short_type(ret)
        mods=[]
        try:
            for f in ('acc_public','acc_private','acc_protected','acc_static','acc_final','acc_abstract','acc_synchronized'):
                if m.access_flags.get(f): mods.append(f[4:])
        except Exception: pass
        head=f"{' '.join(mods)+' ' if mods else ''}{rets} {name}({', '.join(pl)}) {{"
        body="\n".join("    "+l for l in lines)
        return f"{head}\n{body}\n}}\n"

def decompile(root, rel, outpath):
    cl = ClassLoader(root)
    cf = cl.load(rel)
    d = Decompiler(cf)
    buf=[f"// class {rel}  (classfile v{cf.version})\n"]
    try:
        buf.append(f"// extends {cname(cf.super_.name) if cf.super_ else None}\n")
    except Exception: pass
    for f in cf.fields:
        try:
            buf.append(f"field {cname(f.name)} {cname(f.descriptor)};\n")
        except Exception: pass
    for m in cf.methods:
        try:
            buf.append(d.render_method(m))
            buf.append("\n")
        except Exception as e:
            buf.append(f"// DECOMPILE ERROR in {m.name.value if hasattr(m.name,'value') else m.name}: {e}\n")
    open(outpath,'w').write("".join(buf))

if __name__=='__main__':
    root, rel, outp = sys.argv[1], sys.argv[2], sys.argv[3]
    decompile(root, rel, outp)
