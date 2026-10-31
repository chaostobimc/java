// class net/friendswithmoney/fwm/antileak/FakeCoords  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field RANDOM Ljava/security/SecureRandom;;
field TELEPORT D;
field EDGE D;
field RANGE D;
field XYZ Ljava/util/regex/Pattern;;
field BLOCK Ljava/util/regex/Pattern;;
field CHUNK Ljava/util/regex/Pattern;;
field SECTION Ljava/util/regex/Pattern;;
field TARGETED Ljava/util/regex/Pattern;;
field world Lnet/minecraft/world/level/Level;;
field started Z;
field realX D;
field realY D;
field realZ D;
field fakeX D;
field fakeY D;
field fakeZ D;
field heading D;
field turn D;
private void <init>() {
    return;
}

public static boolean active() {
    if (config.Features.isOn("fwm.feature.antileak") ==0) goto L21;
    if (config.FwmConfig.INSTANCE.antiLeakCoords ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public static boolean rewrite(List lines) {
    if (lines == null) goto L10;
    if (antileak.FakeCoords.active() !=0) goto L12;
    L10:
    return lines;
    L12:
    v1 = client.Minecraft.getInstance().getCameraEntity();
    if (v1 != null) goto L25;
    return lines;
    L25:
    antileak.FakeCoords.follow(v1);
    v2 = antileak.FakeCoords.floor(antileak.FakeCoords.fakeX);
    v3 = antileak.FakeCoords.floor(antileak.FakeCoords.fakeY);
    v4 = antileak.FakeCoords.floor(antileak.FakeCoords.fakeZ);
    v5 = v1.getBlockX();
    v6 = v1.getBlockY();
    v7 = v1.getBlockZ();
    v8 = ?;
    v9 = lines.iterator();
    L92:
    if (v9.hasNext() ==0) goto L633;
    v10 = ((String)v9.next());
    if (v10 != null) goto L131;
    v8.add(null);
    goto L92;
    L131:
    v11 = v10.replaceAll("\u00a7.", "");
    if (antileak.FakeCoords.XYZ.matcher(v11).matches() ==0) goto L206;
    new Object[3][0] = Double.valueOf(antileak.FakeCoords.fakeX);
    new Object[3][1] = Double.valueOf(antileak.FakeCoords.fakeY);
    new Object[3][2] = Double.valueOf(antileak.FakeCoords.fakeZ);
    v8.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", new Object[3]));
    goto L630;
    L206:
    if (antileak.FakeCoords.BLOCK.matcher(v11).lookingAt() ==0) goto L332;
    if (v11.contains("[") ==0) goto L276;
    new Object[3][0] = Integer.valueOf((v2 & 15));
    new Object[3][1] = Integer.valueOf((v3 & 15));
    new Object[3][2] = Integer.valueOf((v4 & 15));
    goto L278;
    L276:
    L278:
    v12 = "";
    new Object[3][0] = Integer.valueOf(v2);
    new Object[3][1] = Integer.valueOf(v3);
    new Object[3][2] = Integer.valueOf(v4);
    v8.add(String.format(Locale.ROOT, "Block: %d %d %d", new Object[3]) + v12);
    goto L630;
    L332:
    if (antileak.FakeCoords.CHUNK.matcher(v11).matches() ==0) goto L449;
    v12 = (v2 >> 4);
    v13 = (v4 >> 4);
    new Object[7][0] = Integer.valueOf(v12);
    new Object[7][1] = Integer.valueOf((v3 >> 4));
    new Object[7][2] = Integer.valueOf(v13);
    new Object[7][3] = Integer.valueOf((v12 & 31));
    new Object[7][4] = Integer.valueOf((v13 & 31));
    new Object[7][5] = Integer.valueOf((v12 >> 5));
    new Object[7][6] = Integer.valueOf((v13 >> 5));
    v8.add(String.format(Locale.ROOT, "Chunk: %d %d %d [%d %d in r.%d.%d.mca]", new Object[7]));
    goto L630;
    L449:
    if (antileak.FakeCoords.SECTION.matcher(v11).matches() ==0) goto L517;
    new Object[3][0] = Integer.valueOf((v2 & 15));
    new Object[3][1] = Integer.valueOf((v3 & 15));
    new Object[3][2] = Integer.valueOf((v4 & 15));
    v8.add(String.format(Locale.ROOT, "Section-relative: %02d %02d %02d", new Object[3]));
    goto L630;
    L517:
    v12 = antileak.FakeCoords.TARGETED.matcher(v10);
    if (v12.find() ==0) goto L620;
    v13 = ((v2 + Integer.parseInt(v12.group(2))) - v5);
    v14 = ((v3 + Integer.parseInt(v12.group(3))) - v6);
    v15 = ((v4 + Integer.parseInt(v12.group(4))) - v7);
    v8.add(v12.replaceFirst(regex.Matcher.quoteReplacement(v12.group(1) + v13 + ', ' + v14 + ', ' + v15)));
    goto L630;
    L620:
    v8.add(v10);
    L630:
    goto L92;
    L633:
    return v8;
}

private static double follow(world.entity.Entity camera) {
    v1 = camera.getX();
    v3 = camera.getY();
    v5 = camera.getZ();
    if (antileak.FakeCoords.started ==0) goto L32;
    if (camera.level() == antileak.FakeCoords.world) goto L41;
    L32:
    antileak.FakeCoords.restart(camera, v1, v3, v5);
    return;
    L41:
    v7 = (v1 - antileak.FakeCoords.realX);
    v9 = (v3 - antileak.FakeCoords.realY);
    v11 = (v5 - antileak.FakeCoords.realZ);
    antileak.FakeCoords.realX = v1;
    antileak.FakeCoords.realY = v3;
    antileak.FakeCoords.realZ = v5;
    v13 = Math.sqrt(((v7 * v7) + (v11 * v11)));
    if (cmp(v13,24.0) >0) goto L113;
    if (cmp(Math.abs(v9),24.0) <=0) goto L122;
    L113:
    antileak.FakeCoords.restart(camera, v1, v3, v5);
    return;
    L122:
    if (cmp(v13,1e-06) <=0) goto L227;
    antileak.FakeCoords.turn = ((antileak.FakeCoords.turn * 0.95) + (antileak.FakeCoords.RANDOM.nextGaussian() * 0.01));
    if (antileak.FakeCoords.RANDOM.nextInt(400) !=0) goto L185;
    antileak.FakeCoords.turn = (antileak.FakeCoords.turn + ((antileak.FakeCoords.RANDOM.nextDouble() - 0.5) * 0.6));
    L185:
    antileak.FakeCoords.heading = (antileak.FakeCoords.heading + antileak.FakeCoords.turn);
    antileak.FakeCoords.fakeX = (antileak.FakeCoords.fakeX + (Math.cos(antileak.FakeCoords.heading) * v13));
    antileak.FakeCoords.fakeZ = (antileak.FakeCoords.fakeZ + (Math.sin(antileak.FakeCoords.heading) * v13));
    L227:
    antileak.FakeCoords.fakeY = (antileak.FakeCoords.fakeY + v9);
    return;
}

private static double restart(world.entity.Entity camera, double x, double a2, double y) {
    antileak.FakeCoords.world = camera.level();
    antileak.FakeCoords.started = 1;
    antileak.FakeCoords.realX = x;
    antileak.FakeCoords.realY = y;
    antileak.FakeCoords.realZ = v5;
    v7 = antileak.FakeCoords.world.getWorldBorder();
    v8 = Math.max(400.0, (Math.min((v7.getSize() / 2.0), 250000.0) - 200.0));
    antileak.FakeCoords.fakeX = (v7.getCenterX() + (((antileak.FakeCoords.RANDOM.nextDouble() * 2.0) - 1) * v8));
    antileak.FakeCoords.fakeZ = (v7.getCenterZ() + (((antileak.FakeCoords.RANDOM.nextDouble() * 2.0) - 1) * v8));
    v10 = antileak.FakeCoords.world.getMinY();
    antileak.FakeCoords.fakeY = (((d)((v10 + 8) + antileak.FakeCoords.RANDOM.nextInt(Math.max(16, (180 - v10))))) + antileak.FakeCoords.RANDOM.nextDouble());
    antileak.FakeCoords.heading = ((antileak.FakeCoords.RANDOM.nextDouble() * 3.141592653589793) * 2.0);
    antileak.FakeCoords.turn = 0;
    return;
}

private static double floor(double value) {
    return ((i)Math.floor(value));
}

static regex.Pattern <clinit>() {
    antileak.FakeCoords.RANDOM = ?;
    antileak.FakeCoords.XYZ = regex.Pattern.compile("^XYZ: .*");
    antileak.FakeCoords.BLOCK = regex.Pattern.compile("^Block: -?\\d+ -?\\d+ -?\\d+");
    antileak.FakeCoords.CHUNK = regex.Pattern.compile("^Chunk: .*");
    antileak.FakeCoords.SECTION = regex.Pattern.compile("^Section-relative: .*");
    antileak.FakeCoords.TARGETED = regex.Pattern.compile("(Targeted (?:Block|Fluid): )(-?\\d+), (-?\\d+), (-?\\d+)");
    return;
}

