// class net/friendswithmoney/fwm/net/RtpPoints$Dim  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field OVERWORLD Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
field NETHER Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
field END Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
field $VALUES [Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.RtpPoints$Dim;)net.RtpPoints$Dim.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.RtpPoints$Dim)Enum.valueOf(net.RtpPoints$Dim.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

public String key() {
    return this.name().toLowerCase(Locale.ROOT);
}

public static net.RtpPoints$Dim of(client.multiplayer.ClientLevel world) {
    if (world != null) goto L8;
    return net.RtpPoints$Dim.OVERWORLD;
    L8:
    v1 = world.dimension();
    if (v1 != world.level.Level.NETHER) goto L24;
    return net.RtpPoints$Dim.NETHER;
    L24:
    if (v1 != world.level.Level.END) goto L35;
    return net.RtpPoints$Dim.END;
    L35:
    v2 = world.dimensionTypeRegistration();
    return net.RtpPoints$Dim.of(v1.identifier().getPath(), v2.is(world.level.dimension.BuiltinDimensionTypes.NETHER), v2.is(world.level.dimension.BuiltinDimensionTypes.END));
}

public static boolean of(String path, boolean netherType, boolean endType) {
    if (netherType ==0) goto L8;
    return net.RtpPoints$Dim.NETHER;
    L8:
    if (endType ==0) goto L16;
    return net.RtpPoints$Dim.END;
    L16:
    if (path != null) goto L25;
    goto L32;
    L25:
    L32:
    v3 = path.toLowerCase(Locale.ROOT);
    if (v3.contains("nether") ==0) goto L46;
    return net.RtpPoints$Dim.NETHER;
    L46:
    if (v3.equals("end") !=0) goto L73;
    if (v3.endsWith("_end") !=0) goto L73;
    if (v3.contains("the_end") ==0) goto L77;
    L73:
    return net.RtpPoints$Dim.END;
    L77:
    return net.RtpPoints$Dim.OVERWORLD;
}

private static net.RtpPoints$Dim[] $values() {
    new net.RtpPoints$Dim[3][0] = net.RtpPoints$Dim.OVERWORLD;
    new net.RtpPoints$Dim[3][1] = net.RtpPoints$Dim.NETHER;
    new net.RtpPoints$Dim[3][2] = net.RtpPoints$Dim.END;
    return new net.RtpPoints$Dim[3];
}

static net.RtpPoints$Dim[] <clinit>() {
    net.RtpPoints$Dim.OVERWORLD = ?;
    net.RtpPoints$Dim.NETHER = ?;
    net.RtpPoints$Dim.END = ?;
    net.RtpPoints$Dim.$VALUES = net.RtpPoints$Dim.$values();
    return;
}

