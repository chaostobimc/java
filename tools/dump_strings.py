import sys, os, json
from jawa.classloader import ClassLoader
from jawa.constants import UTF8

root, out = sys.argv[1], sys.argv[2]
cl = ClassLoader(root)
rows=[]
for dirpath,_,files in os.walk(root):
    for f in sorted(files):
        if not f.endswith('.class'): continue
        rel=os.path.relpath(os.path.join(dirpath,f),root)
        name=rel.replace(os.sep,'/')[:-6]
        cf=cl.load(name)
        strs=[c.value for c in cf.constants if isinstance(c,UTF8)]
        rows.append({'class':rel[:-6],'version':cf.version,'strings':strs})
json.dump(rows,open(out,'w'),indent=0)
print("classes:",len(rows),"utf8:",sum(len(r['strings']) for r in rows))
