// class net/friendswithmoney/fwm/config/FwmConfig$SpawnerSpot  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field x I;
field y I;
field z I;
field dim Ljava/lang/String;;
field server Ljava/lang/String;;
field mode Ljava/lang/String;;
field sellFill I;
field dropMin I;
field kind Ljava/lang/String;;
field loot Ljava/lang/String;;
public void <init>() {
    this.dim = "";
    this.server = "";
    this.mode = "bones";
    this.sellFill = 80;
    this.dropMin = 2304;
    return;
}

public void <init>(int x, int y, int z, String dim, String server) {
    this.dim = "";
    this.server = "";
    this.mode = "bones";
    this.sellFill = 80;
    this.dropMin = 2304;
    this.x = x;
    this.y = y;
    this.z = z;
    this.dim = dim;
    this.server = server;
    return;
}

