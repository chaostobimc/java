import os, sys, traceback
sys.path.insert(0,'analysis')
from dec import decompile
root='x'; outdir='decompiled'
os.makedirs(outdir, exist_ok=True)
n=0; errs=0
for dp,_,fs in os.walk(root):
    for f in sorted(fs):
        if not f.endswith('.class'): continue
        rel=os.path.relpath(os.path.join(dp,f),root).replace(os.sep,'/')[:-6]
        outp=os.path.join(outdir, rel+'.java')
        os.makedirs(os.path.dirname(outp), exist_ok=True)
        try:
            decompile(root, rel, outp); n+=1
        except Exception as e:
            errs+=1; print('FAIL',rel,e)
print('ok',n,'fail',errs)
