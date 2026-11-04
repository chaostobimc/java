#!/usr/bin/env python3
"""Rebuild the small compatibility/theme patch against compile-only API stubs."""
import hashlib, json, os, pathlib, subprocess, sys, tempfile, zipfile

ROOT = pathlib.Path(__file__).resolve().parent.parent
ORIGINAL = ROOT / 'fwm-1.10.6.6_MC-26.3.jar'
OUTPUT = ROOT / 'fwm-1.10.6.6-clean-fix_MC-26.3.jar'
# Avoid silently patching a different binary with potentially incompatible descriptors.
EXPECTED = '238fa93161a72437b90e764f5fd50f840cf449bfb4140f8813c11817adb353e0'
if hashlib.sha256(ORIGINAL.read_bytes()).hexdigest() != EXPECTED:
    sys.exit('Original-JAR stimmt nicht mit der erwarteten Version überein.')
with tempfile.TemporaryDirectory() as temp:
    classes = pathlib.Path(temp) / 'classes'
    sources = sorted(str(p) for base in ('patch/stubs', 'patch/src') for p in (ROOT / base).rglob('*.java'))
    ecj = os.environ.get('ECJ_JAR')
    compiler = [os.environ.get('JAVA', 'java'), '-jar', ecj, '-17'] if ecj else [os.environ.get('JAVAC', 'javac'), '--release', '17']
    subprocess.run(compiler + ['-proc:none', '-d', str(classes)] + sources, check=True)
    files = {
        'net/friendswithmoney/fwm/mixin/KeyboardBoundsMixin.class': classes / 'net/friendswithmoney/fwm/mixin/KeyboardBoundsMixin.class',
        'net/friendswithmoney/fwm/ui/FwmTheme.class': classes / 'net/friendswithmoney/fwm/ui/FwmTheme.class',
    }
    with zipfile.ZipFile(ORIGINAL) as src, zipfile.ZipFile(OUTPUT, 'w') as dst:
        for item in src.infolist():
            if item.filename in files:
                data = files.pop(item.filename).read_bytes()
            else:
                data = src.read(item.filename)
            if item.filename == 'fwm.mixins.json':
                config = json.loads(data)
                config['client'].append('KeyboardBoundsMixin')
                data = (json.dumps(config, indent=2) + '\n').encode()
            elif item.filename == 'fabric.mod.json':
                config = json.loads(data)
                config['version'] = '1.10.6.6+cleanfix.1'
                config['depends']['minecraft'] = '=26.3'
                data = (json.dumps(config, indent=2, ensure_ascii=False) + '\n').encode()
            dst.writestr(item, data)
        for name, path in files.items():
            dst.writestr(name, path.read_bytes(), compress_type=zipfile.ZIP_DEFLATED)
print(f'Erstellt: {OUTPUT}')
