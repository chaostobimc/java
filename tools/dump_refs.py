import sys, os, json
from jawa.classloader import ClassLoader
from jawa.constants import MethodReference, FieldReference, ConstantClass, UTF8
root, out = sys.argv[1], sys.argv[2]
cl = ClassLoader(root)
rows=[]
for dirpath,_,files in os.walk(root):
    for f in sorted(files):
        if not f.endswith('.class'): continue
        rel=os.path.relpath(os.path.join(dirpath,f),root)
        cf=cl.load(rel.replace(os.sep,'/')[:-6])
        meth=set(); fld=set()
        for c in cf.constants:
            if isinstance(c,MethodReference):
                meth.add(f"{c.class_.name.value}.{c.name_and_type.name.value}{c.name_and_type.descriptor.value}")
            elif isinstance(c,FieldReference):
                fld.add(f"{c.class_.name.value}.{c.name_and_type.name.value}")
        rows.append({'class':rel[:-6],'methods':sorted(meth),'fields':sorted(fld)})
json.dump(rows,open(out,'w'),indent=0)
print('classes',len(rows))
