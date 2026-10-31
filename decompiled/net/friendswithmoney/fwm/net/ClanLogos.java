// class net/friendswithmoney/fwm/net/ClanLogos  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FOLDER Ljava/lang/String;;
field PACK_ID Ljava/lang/String;;
field READY Ljava/util/Map;;
field LOADING Ljava/util/Set;;
field active Ljava/util/Set;;
field checkedAtStart Z;
field nextIcon J;
private void <init>() {
    return;
}

private static Path cacheDir() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-clan-logos");
}

public static resources.Identifier font(String id) {
    if (id != null) goto L6;
    return null;
    L6:
    if (net.ClanLogos.active.contains(id) ==0) goto L30;
    return resources.Identifier.fromNamespaceAndPath("fwm", 'clan_' + id);
    L30:
    if ("mrket".equals(id) ==0) goto L47;
    return resources.Identifier.fromNamespaceAndPath("fwm", "market");
    L47:
    return null;
}

private static char versionOf(String etag) {
    if (etag == null) goto L11;
    if (etag.isBlank() ==0) goto L13;
    L11:
    return 0;
    L13:
    v1 = 1125899906842597;
    v3 = 0;
    L19:
    if (v3 >= etag.length()) goto L46;
    v1 = ((31 * v1) + ((l)etag.charAt(v3)));
    v3 = v3 + 1;
    goto L19;
    L46:
    return ((v1 & 9223372036854775807) | 1);
}

private static byte[] shrink(byte[] png) {
    v1 = ImageIO.read(?);
    if (v1 != null) goto L18;
    return null;
    L18:
    v2 = 30;
    v3 = v1;
    v4 = v1.getWidth();
    v5 = v1.getHeight();
    v6 = Math.max(v4, v5);
    v7 = ?;
    v8 = v7.createGraphics();
    v8.drawImage(v3, ((v6 - v4) / 2), ((v6 - v5) / 2), null);
    v8.dispose();
    v3 = v7;
    L95:
    if ((v3.getWidth() / 2) < v2) goto L170;
    v9 = (v3.getWidth() / 2);
    v10 = ?;
    v11 = v10.createGraphics();
    v11.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    v11.drawImage(v3, 0, 0, v9, v9, null);
    v11.dispose();
    v3 = v10;
    goto L95;
    L170:
    v9 = ?;
    v10 = v9.createGraphics();
    v10.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    v10.drawImage(v3, 0, 0, v2, v2, null);
    v10.dispose();
    v11 = ?;
    ImageIO.write(v9, "png", v11);
    return v11.toByteArray();
}

static void tick(client.Minecraft client, List clanList, List playerList) {
    v3 = ?;
    v4 = clanList.iterator();
    L16:
    if (v4.hasNext() ==0) goto L119;
    v5 = ((net.TrustedClans$Clan)v4.next());
    if (v5.iconUrl == null) goto L86;
    v3.add(?);
    goto L116;
    L86:
    v3.add(?);
    L116:
    goto L16;
    L119:
    v4 = playerList.iterator();
    L127:
    if (v4.hasNext() ==0) goto L195;
    v5 = ((net.TrustedClans$Player)v4.next());
    if (v5.uuid == null) goto L192;
    v3.add(?);
    L192:
    goto L127;
    L195:
    v4 = v3.iterator();
    L203:
    if (v4.hasNext() ==0) goto L601;
    v5 = ((net.ClanLogos$Logo)v4.next());
    if (cmp(v5.logoVersion(),0) <=0) goto L203;
    if (v5.logo() == null) goto L203;
    if (v5.logo().isBlank() !=0) goto L203;
    if (v5.id() == null) goto L203;
    if (v5.id().matches("[a-z0-9_-]{2,24}|p_[0-9a-f]{32}") !=0) goto L278;
    goto L203;
    L278:
    v6 = ((Long)net.ClanLogos.READY.get(v5.id()));
    if (v6 == null) goto L318;
    if (cmp(v6.longValue(),v5.logoVersion()) !=0) goto L318;
    goto L203;
    L318:
    v7 = net.ClanLogos.cacheDir().resolve(v5.id() + '-' + v5.logoVersion() + '.png');
    if (Files.exists(v7, new LinkOption[0]) ==0) goto L380;
    net.ClanLogos.READY.put(v5.id(), Long.valueOf(v5.logoVersion()));
    goto L203;
    L380:
    v8 = v5.logo().startsWith("http");
    if (v8 ==0) goto L411;
    if (cmp(System.currentTimeMillis(),net.ClanLogos.nextIcon) >=0) goto L411;
    goto L203;
    L411:
    if (net.ClanLogos.LOADING.add(v5.id()) !=0) goto L430;
    goto L203;
    L430:
    v9 = v5.id();
    v10 = v5.logoVersion();
    if (v8 ==0) goto L551;
    net.ClanLogos.nextIcon = (System.currentTimeMillis() + 1000);
    v12 = net.ClanLogos.cacheDir().resolve(v9 + '.etag');
    v13 = null;
    if (Files.exists(v12, new LinkOption[0]) ==0) goto L504;
    v13 = Files.readString(v12, StandardCharsets.UTF_8).trim();
    L504:
    goto L509;
    v14 = /*catch Exception*/ ex;
    L509:
    v14 = v13;
    net.FwmApi.request("GET", v5.logo(), v14, null, null, 2097152).whenComplete(/*indy*/accept(v9, v7, v10, v12));
    goto L203;
    L551:
    v12 = config.FwmConfig.INSTANCE.apiBaseUrl.replaceAll("/+$", "") + v5.logo();
    net.FwmApi.getBytes(v12).whenComplete(/*indy*/accept(v7, v9, v10));
    goto L203;
    L601:
    if (client.gui.overlay() != null) goto L621;
    if (client.gui.screen() == null) goto L622;
    L621:
    return;
    L622:
    v4 = ?;
    v5 = v3.iterator();
    L639:
    if (v5.hasNext() ==0) goto L726;
    v6 = ((net.ClanLogos$Logo)v5.next());
    v7 = ((Long)net.ClanLogos.READY.get(v6.id()));
    if (v7 == null) goto L723;
    if (cmp(v6.logoVersion(),0) <=0) goto L723;
    if (cmp(v7.longValue(),v6.logoVersion()) !=0) goto L723;
    v4.put(v6.id(), v7);
    L723:
    goto L639;
    L726:
    if (v4.isEmpty() ==0) goto L742;
    goto L755;
    L742:
    L755:
    v5 = 'v1|' + antileak.AntiLeakPack.packFormat() + '|' + String.valueOf(v4);
    if (net.ClanLogos.checkedAtStart !=0) goto L809;
    net.ClanLogos.checkedAtStart = 1;
    if (v5.equals(config.FwmConfig.INSTANCE.clanLogosApplied) ==0) goto L809;
    if (Files.isDirectory(net.ClanLogos.folder(client), new LinkOption[0]) ==0) goto L809;
    net.ClanLogos.active = Set.copyOf(v4.keySet());
    return;
    L809:
    if (v5.equals(config.FwmConfig.INSTANCE.clanLogosApplied) ==0) goto L824;
    return;
    L824:
    if (v4.isEmpty() !=0) goto L849;
    if (v4.size() >= net.ClanLogos.countWithLogo(v3)) goto L849;
    return;
    L849:
    net.ClanLogos.apply(client, v4, v5);
    return;
}

private static long countWithLogo(List clans) {
    v1 = 0;
    v2 = clans.iterator();
    L9:
    if (v2.hasNext() ==0) goto L43;
    v3 = ((net.ClanLogos$Logo)v2.next());
    if (cmp(v3.logoVersion(),0) <=0) goto L40;
    v1 = v1 + 1;
    L40:
    goto L9;
    L43:
    return v1;
}

private static Path folder(client.Minecraft client) {
    return client.getResourcePackDirectory().resolve("FWM Clan-Logos");
}

private static void apply(client.Minecraft client, Map wanted, String key) {
    v3 = net.ClanLogos.folder(client);
    v4 = client.getResourcePackRepository();
    v5 = v4.getSelectedIds().contains("file/FWM Clan-Logos");
    if (wanted.isEmpty() !=0) goto L39;
    goto L40;
    L39:
    L40:
    v6 = 0;
    antileak.AntiLeakPack.delete(v3);
    if (v6 ==0) goto L285;
    v7 = v3.resolve("assets/fwm/font");
    v8 = v3.resolve("assets/fwm/textures/font");
    Files.createDirectories(v7, new attribute.FileAttribute[0]);
    Files.createDirectories(v8, new attribute.FileAttribute[0]);
    Files.writeString(v3.resolve("pack.mcmeta"), antileak.AntiLeakPack.mcmeta(), StandardCharsets.UTF_8, new OpenOption[0]);
    v9 = wanted.entrySet().iterator();
    L129:
    if (v9.hasNext() ==0) goto L245;
    v10 = ((Map$Entry)v9.next());
    v11 = ((String)v10.getKey());
    Files.copy(net.ClanLogos.cacheDir().resolve(v11 + '-' + String.valueOf(v10.getValue()) + '.png'), v8.resolve('clan_' + v11 + '.png'), new CopyOption[0]);
    Files.writeString(v7.resolve('clan_' + v11 + '.json'), '{"providers":[{"type":"bitmap","file":"fwm:font/clan_' + v11 + '.png","ascent":8,"height":10,"chars":["\\ue000"]}]}', StandardCharsets.UTF_8, new OpenOption[0]);
    goto L129;
    L245:
    v4.reload();
    if (v5 !=0) goto L282;
    if (v4.addPack("file/FWM Clan-Logos") !=0) goto L282;
    System.err.println('[FWM] Clan logo pack not found after writing it: ' + String.valueOf(v3));
    return;
    L282:
    goto L294;
    L285:
    v4.removePack("file/FWM Clan-Logos");
    L294:
    client.options.updateResourcePacks(v4);
    if (v6 ==0) goto L318;
    if (v5 ==0) goto L318;
    client.reloadResourcePacks();
    L318:
    net.ClanLogos.active = Set.copyOf(wanted.keySet());
    config.FwmConfig.INSTANCE.clanLogosApplied = key;
    config.ConfigManager.save();
    goto L361;
    v7 = /*catch Exception*/ ex;
    System.err.println('[FWM] Clan logos could not be applied: ' + String.valueOf(v7));
    L361:
    return;
}

private static boolean lambda$tick$1(Path file, String logoId, long version, byte[] a3, Throwable bytes) {
    if (v5 != null) goto L86;
    if (bytes == null) goto L86;
    if (bytes.length <= 8) goto L86;
    if (bytes.length >= 64000) goto L86;
    if ((bytes[0] & 255) != 137) goto L86;
    if (bytes[1] != 80) goto L86;
    Files.createDirectories(net.ClanLogos.cacheDir(), new attribute.FileAttribute[0]);
    Files.write(file, bytes, new OpenOption[0]);
    net.ClanLogos.READY.put(logoId, Long.valueOf(version));
    L86:
    net.ClanLogos.LOADING.remove(logoId);
    goto L129;
    v6 = /*catch Exception*/ ex;
    net.ClanLogos.LOADING.remove(logoId);
    goto L129;
    v7 = /*catch any*/ ex;
    net.ClanLogos.LOADING.remove(logoId);
    throw v7;
    L129:
    return;
}

private static boolean lambda$tick$0(String logoId, Path file, long version, Path a3, net.FwmApi$Fetched etagFile, Throwable got) {
    if (v6 == null) goto L16;
    net.ClanLogos.LOADING.remove(logoId);
    return;
    L16:
    if (got.status() != 429) goto L60;
    net.ClanLogos.nextIcon = (System.currentTimeMillis() + (Math.max(60, got.retryAfterSeconds()) * 1000));
    net.ClanLogos.LOADING.remove(logoId);
    return;
    L60:
    Files.createDirectories(net.ClanLogos.cacheDir(), new attribute.FileAttribute[0]);
    v7 = net.ClanLogos.cacheDir().resolve(logoId + '-last.png');
    if (got.status() != 304) goto L152;
    if (Files.exists(v7, new LinkOption[0]) ==0) goto L152;
    new CopyOption[1][0] = StandardCopyOption.REPLACE_EXISTING;
    Files.copy(v7, file, new CopyOption[1]);
    net.ClanLogos.READY.put(logoId, Long.valueOf(version));
    net.ClanLogos.LOADING.remove(logoId);
    return;
    L152:
    v8 = got.body();
    if (got.status() != 200) goto L206;
    if (v8 == null) goto L206;
    if (v8.length < 8) goto L206;
    if ((v8[0] & 255) != 137) goto L206;
    if (v8[1] == 80) goto L217;
    L206:
    net.ClanLogos.LOADING.remove(logoId);
    return;
    L217:
    v9 = net.ClanLogos.shrink(v8);
    if (v9 != null) goto L240;
    net.ClanLogos.LOADING.remove(logoId);
    return;
    L240:
    Files.write(file, v9, new OpenOption[0]);
    Files.write(v7, v9, new OpenOption[0]);
    if (got.etag() == null) goto L289;
    Files.writeString(etagFile, got.etag(), StandardCharsets.UTF_8, new OpenOption[0]);
    L289:
    net.ClanLogos.READY.put(logoId, Long.valueOf(version));
    net.ClanLogos.LOADING.remove(logoId);
    goto L346;
    v7 = /*catch Exception*/ ex;
    net.ClanLogos.LOADING.remove(logoId);
    goto L346;
    v10 = /*catch any*/ ex;
    net.ClanLogos.LOADING.remove(logoId);
    throw v10;
    L346:
    return;
}

static Set <clinit>() {
    net.ClanLogos.READY = ?;
    net.ClanLogos.LOADING = concurrent.ConcurrentHashMap.newKeySet();
    net.ClanLogos.active = Set.of();
    return;
}

