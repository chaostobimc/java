// class net/friendswithmoney/fwm/net/RtpPoints$Cloud  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field points [I;
field index Lnet/friendswithmoney/fwm/net/RtpPoints$Index;;
field area I;
field fetchedAt J;
field busy Z;
field state Lnet/friendswithmoney/fwm/net/RtpPoints$State;;
private void <init>() {
    this.points = new int[0];
    this.area = 5000;
    this.state = net.RtpPoints$State.EMPTY;
    return;
}

