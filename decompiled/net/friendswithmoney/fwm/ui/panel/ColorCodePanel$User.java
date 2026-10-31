// class net/friendswithmoney/fwm/ui/panel/ColorCodePanel$User  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field code Lnet/friendswithmoney/fwm/colorcode/ColorCode;;
field mode Lnet/friendswithmoney/fwm/colorcode/ColorCode$Mode;;
field version I;
field owner Lnet/friendswithmoney/fwm/ui/panel/ColorCodePanel;;
void <init>() {
    this.code = ?;
    this.mode = colorcode.ColorCode$Mode.RENAME;
    return;
}

void load(colorcode.ColorCode content, colorcode.ColorCode$Mode newMode) {
    this.code.set(content);
    if (newMode == null) goto L17;
    this.mode = newMode;
    L17:
    this.version = (this.version + 1);
    return;
}

