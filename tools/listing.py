import sys
sys.path.insert(0,'/home/user/java/work/analysis')
from jawa.classloader import ClassLoader
from jawa.constants import *
from dec import Fmt, simp, short_type

def fmt_const(f, c):
    k = f.const(c)
    if k[0]=='method': return f"{simp(k[1])}.{k[2]}{short_type('L'+k[3]+';') if False else k[3]}"
    if k[0]=='field': return f"{simp(k[1])}.{k[2]} : {k[3]}"
    if k[0]=='str': return repr(k[1])
    if k[0]=='class': return k[1]
    if k[0]=='indy': return f"indy {k[1]}{k[2]}"
    return str(k[1])

def main(root, cls, names):
    cl=ClassLoader(root); cf=cl.load(cls); f=Fmt(cf)
    print(f"// {cls} v{cf.version}")
    for fl in cf.fields:
        print(f"field {fl.name.value} {fl.descriptor.value}")
    for m in cf.methods:
        if names and m.name.value not in names: continue
        print(f"\n=== {m.name.value}{m.descriptor.value}")
        if not m.code: print("  (no code)"); continue
        for e in (m.code.exception_table or []):
            print(f"  [try {e.start_pc}..{e.end_pc} handler {e.handler_pc} type {e.catch_type}]")
        for ins in m.code.disassemble():
            ops=[]
            for o in ins.operands:
                if isinstance(o, dict):
                    ops.append(repr(o)); continue
                if o.op_type.name=='CONSTANT_INDEX':
                    try: ops.append(fmt_const(f, f.get(o.value)))
                    except Exception: ops.append(f"#{o.value}")
                elif o.op_type.name=='BRANCH':
                    ops.append(f"{ins.pos+o.value}")
                elif o.op_type.name=='LOCAL_INDEX':
                    ops.append(f"v{o.value}")
                else: ops.append(str(o.value))
            print(f"  {ins.pos:5d} {ins.mnemonic:16s} {' '.join(ops)}")

if __name__=='__main__':
    root, cls = sys.argv[1], sys.argv[2]
    names = set(sys.argv[3:]) if len(sys.argv)>3 else set()
    main(root, cls, names)
