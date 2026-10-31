# Sicherheitsanalyse – „FWM Mod“ 1.10.6.6 (Minecraft 26.3, Fabric)

**Gegenstand:** `fwm-1.10.6.6_MC-26.3.jar` (1,53 MB, 443 Jar-Einträge, 382 Klassen)
**Autor/Marke:** „Friends With Money“ / HUGOSMP ([friendswithmoney.net](https://friendswithmoney.net))
**Datum:** 2026-10-31
**Art der Prüfung:** Statische Analyse des dekompilierten Client-Codes (Whitebox), keine Laufzeit-/Penetrationstests gegen Backend oder Spielserver.
**Ergebnis in einem Satz:** Keine Hintertür, kein Remote-Code-Execution, kein Diebstahl von Minecraft-/Launcher-Sessions, **keine Rechte-Eskalation durch Mitspieler** – aber mehrere Datenschutz-, Härtungs- und Verfügbarkeitsprobleme, darunter eine **stille UUID-/Nutzernamen-Übermittlung an den Hersteller**, ein **servergesteuerter Kill-Switch**, eine umgehbare Chat-Heuristik (F-14, Abschnitt 6) und ein namensbasiertes Badge-Spoofing (F-13).

---

## 1. Vorgehen und Werkzeuge

Im Sandbox stand kein JDK/JRE zur Verfügung (keine `java`-Binary, kein Decompiler-Jar lauffähig, Download externer Decompiler blockiert). Deshalb wurde ein eigener Bytecode-Decompiler auf Basis der Python-Bibliothek **jawa** geschrieben:

| Werkzeug | Zweck |
|---|---|
| `tools/dec.py` | Stack-basierter Decompiler (Konstanten-Auflösung, `invokedynamic`/String-Concat-Rekonstruktion, `LocalVariableTable`, Exception-Handler) |
| `tools/run_dec.py` | Batch-Lauf über alle Klassen → `decompiled/**/*.java` |
| `tools/listing.py` | javap-ähnliches Bytecode-Listing mit aufgelöstem Konstantenpool → `evidence/*.txt` |
| `tools/dump_strings.py` | alle 46.646 UTF-8-Konstanten je Klasse (Endpunkt-/Secret-Suche) |
| `tools/dump_refs.py` | Cross-Reference aller Methoden-/Feldreferenzen (Suche nach gefährlichen APIs) |

Ergebnis: **382/382 Klassen dekompiliert**, 28 Methoden (v. a. große UI-Render-/Enum-`$values`-Methoden) konnten nicht sauber rekonstruiert werden; diese wurden bei Bedarf im Bytecode gelesen (`evidence/`).

**Wichtige Einschränkung:** Der erzeugte Code ist *Pseudocode* (Goto-Stil, teils unvollständige Verzweigungen, keine generischen Typen). Aussagen unten sind, wo sicherheitsrelevant, zusätzlich mit Bytecode-Listings belegt. Es gab **keine dynamische Analyse** (kein Debugger, kein Traffic-Mitschnitt) und **kein Review des Server-Backends** – nur des Client-Jars.

Technische Eckdaten des Artefakts:

- Classfile-Version **69 (Java 25)**, Fabric-Loader 0.19.3, Minecraft `~26.3`, `fabric-api`, MixinExtras.
- `fabric.mod.json`: `"environment": "client"` – reiner Client-Mod, keine Serverkomponente.
- 18 Mixins (`fwm.mixins.json`), alle clientseitig (Chat, HUD, Renderer, Tastatur/Maus, Container-Klicks).
- Kein Netzwerk-Listener, kein `ServerSocket`, kein `Runtime.exec`/`ProcessBuilder`, kein `URLClassLoader`/`defineClass`, kein `ScriptEngine`, kein `setAccessible`.

---

## 2. Architektur und Vertrauensgrenzen

Der Mod ist ein Komfort-/Handels-Client für einen deutschen Minecraft-Server (HUGOSMP): Sell-Makro, Auktions-/Giveaway-Overlay, privater Chat, Zweit-Chat, 7TV/BTTV-Emotes, Clan-Logos, Marktpreise, RTP-Punkte, Support-Tickets, „Anti-Leak“-Ressourcenpack gegen Koordinaten-Leaks im Stream.

### 2.1 Netzwerk-Endpunkte (alle HTTPS, TLS-Verifikation aktiv)

| Host / Pfad | Richtung | Nutzdaten | Auslöser |
|---|---|---|---|
| `https://269153435.de/api/mod/presence` | POST | `{uuid, name}` (eigene) | nur auf „HugoSMP“-Servern, alle 5 min |
| `…/api/mod/presence/lookup` | POST | `{uuids[]}` **bis 200 fremde Spieler-UUIDs** | dito, bei Bedarf |
| `…/api/mod/identify` | POST | `{uuid, name, modId, modVersion}` | nur auf „HugoSMP“ |
| **`…/api/mod/ban/check`** | POST | `{uuid, name, lockedBy}` | **immer**, jeder Client, ~alle 5–30 min |
| **`…/api/mod/ban/alt`** | POST | `{uuid, name, of, ofUuid}` | wenn ein Sperr-Eintrag existiert |
| `…/api/mod/clans`, `/fakes`, `/news`, `/waypoints`, `/giveaways?uuid=…` | GET/POST | Spieler-UUID in Query | periodisch, **ohne Serverbindung** |
| `…/api/mod/rtp`, `/api/mod/market` | GET | keine | periodisch |
| `https://api.friendswithmoney.net/mod/v1/tickets`, `/spawners` | POST/GET | Ticket-Text + **`Authorization: Bearer fwm_…`** | nur bei Nutzung/Ticket-Polling |
| `https://api.7tv.app/v4/gql`, `api.betterttv.net`, `cdn.7tv.app`, `cdn.betterttv.net` | GET | Suchbegriffe/Emote-IDs | Emote-Picker/Registry |
| `https://api.minecraftservices.com/…/lookup/bulk/byname` | POST | Namensliste | Tab-Liste auflösen |

`http://`-Endpunkte existieren **nicht**; es gibt **keine** hartkodierten Secrets (keine Tokens, Webhooks, Discord-API-Keys im Jar).

### 2.2 Lokale Dateien (alle im Fabric-`config/`-Verzeichnis)

`fwm.json` (Konfiguration, inkl. frei editierbarer API-URLs), **`fwm-key.json` (API-Key im Klartext)**, `fwm-id.json`, `fwm-sperre.json` (Sperr-Notiz inkl. Alt-Liste), `fwm-messages.json` (PN-Inhalte), `fwm-clans.json`, `fwm-trusted-clans.json`, `fwm-fakes.json`, `fwm-market.json`, `fwm-money-log.txt`, `fwm-clan-logos/` (Cache), zusätzlich zwei generierte Ressourcenpacks (`resourcepacks/FWM Anti-Leak`, `FWM Clan-Logos`).

### 2.3 Vertrauensgrenzen

1. **Hersteller-Backend** (`269153435.de`, `api.friendswithmoney.net`) – voll vertrauenswürdig für UI-Daten, Marktpreise, Trust-Badges, Sperren.
2. **Drittanbieter-CDN** (7TV/BTTV) – liefert Bilder, die lokal dekodiert werden.
3. **Minecraft-Server & fremde Spieler** – liefern Chat-Text, Tab-Liste, Klick-Events.
4. **Lokale Dateien** – Konfiguration wird ohne Schema-/Allowlist-Prüfung übernommen.

---

## 3. Befunde

Bewertung: **Kritisch / Hoch / Mittel / Niedrig / Informativ**. Es wurde **kein „Kritisch“** gefunden.

### F-01 – Stille Übermittlung von UUID + Nutzername an den Hersteller (Telemetrie ohne Opt-out)
**Schwere: Mittel** · *CWE-359* · `net/friendswithmoney/fwm/net/ModBan.java:81,93,113` · Beleg: `evidence/ModBan.tick.txt`

```java
// ModBan.tick(Minecraft) – läuft bei jedem Client-Tick, ohne Welt-/Serverprüfung
if (now < nextCheck) return;
nextCheck = now + 300000;                       // 5 min
Check c = new Check(); c.uuid = selfUuid(); c.name = playerName();
FwmApi.post("/api/mod/ban/check", c, null, Answer.class)…   // an apiBaseUrl
```

- Die Prüfung ist **nicht** an HUGOSMP gebunden (im Gegensatz zu `Presence`/`ModId`, die `Presence.onHugo()` prüfen) und **nicht abschaltbar** – es gibt in `FwmConfig` keinen Schalter dafür.
- Sie läuft bereits im Hauptmenü (UUID/Name stammen aus dem GameProfile, nicht aus der Welt).
- Bei bestehendem Sperr-Eintrag wird zusätzlich `/api/mod/ban/alt` mit `{uuid, name, of, ofUuid}` gesendet, d. h. **Account-Verknüpfungen** („Alt-Erkennung“) werden serverseitig gemeldet; die Liste der bereits gemeldeten UUIDs liegt in `fwm-sperre.json`.
- Zusätzlich werden beim Betreten eines Servers, dessen Adresse den Substring `hugosmp` enthält, eigene und **bis zu 200 fremde UUIDs** an `/api/mod/presence/lookup` gesendet (`Presence.java`), sowie `modId`/`modVersion` an `/identify`.

**Auswirkung:** Personenbezogene Daten (Minecraft-UUID = pseudonymes Identifier, Nutzername, Spielverhalten) verlassen den Rechner ohne Kenntnisnahme/Einwilligung; keine Datenschutzhinweise im Mod-UI, kein Opt-out, keine Löschfunktion. Unter DSGVO ist das ein Transparenz-/Rechtsgrundlagenproblem (Art. 6, 13), kein „Hack“.
**Empfehlung:** Telemetrie standardmäßig aus, explizite Einwilligung + Datenschutzerklärung im UI, Serverbindung auch für `ban/check`, Datenminimierung (nur Client-Hash statt UUID), Aufbewahrungs-/Löschkonzept.

---

### F-02 – Serverseitiger Kill-Switch deaktiviert den kompletten Mod
**Schwere: Mittel (Transparenz/Verfügbarkeit)** · `ModBan.java:113` + `config/Features.java:40`, `ui/Design.java:9`

Antwortet `/api/mod/ban/check` mit `banned=true`, legt der Client `fwm-sperre.json` an; `Features.isOn(...)` und `Design.open(...)` prüfen `ModBan.locked()` und blockieren dann sämtliche Mod-Funktionen (Lock-Screen). Der Hersteller kann also **jede Installation remote abschalten** – bewusst als Anti-Missbrauch gedacht, aber ohne Offline-Fallback/Dokumentation ein Single Point of Control (kompromittiertes Backend oder Fehlkonfiguration = Massenausfall).

**Empfehlung:** Signierte Sperr-Listen, klare Kommunikation im UI („warum gesperrt, Widerspruch wohin“), Fail-open bei Serverfehlern (ist implementiert – Sperre wird nur bei expliziter Antwort gesetzt) und Rückfrage/Reset-Möglichkeit.

---

### F-03 – Blinde SSRF: API-gesteuerte Bild-URL wird ungeprüft geladen
**Schwere: Mittel** · *CWE-918* · `net/friendswithmoney/fwm/net/ClanLogos.java:128,149` · Beleg: `evidence/ClanLogos.fetch.txt`

```java
v8 = v5.logo().startsWith("http");
…
FwmApi.request("GET", v5.logo(), etag, null, null, 2097152)   // 2-MB-Cap
    .whenComplete(/* PNG-Magic-Check, shrink(), Cache-Write */);
```

- Das Feld `logo` kommt aus `GET /api/mod/clans`. Beginnt es mit `http`, wird **die URL des Servers 1:1 geladen** (sonst `apiBaseUrl + logo`).
- Es gibt **keine Host-Allowlist**, kein Verbot privater IP-Bereiche, kein `https`-Zwang (`http://…` ist erlaubt).
- Das Ergebnis wird nur als PNG akzeptiert (Magic-Bytes `0x89 0x50`), auf 64 KB geschrumpft und in ein Ressourcenpack geschrieben – der Antwortinhalt ist für den Angreifer also **nicht** direkt lesbar.

**Auswirkung:** Bei kompromittiertem/fehlerhaftem Backend (oder manipulierter `apiBaseUrl` in `fwm.json`): Request-Forgery ins lokale Netz/Intranet (Blind), IP-Leak des Nutzers an beliebige Hosts, paralleles Auslösen von Last (1 Request/s Rate-Limit). Kein Exfiltrationskanal, da die Antwort gefiltert wird.
**Empfehlung:** Relative Pfade erzwingen (nur `apiBaseUrl + path`), Allowlist für Bild-Hosts, `https`-Zwang, private/loopspezifische Adressen blockieren.

---

### F-04 – Ungebremste Bild-Dekodierung → Client-Crash/Freeze (DoS)
**Schwere: Mittel** · *CWE-400 / CWE-789* · `net/friendswithmoney/fwm/seventv/EmoteTexture.java:221` (`readGif`) · Beleg: `evidence/EmoteTexture.readGif.txt`

```java
v3 = reader.getNumImages(true);                       // Frame-Zahl, unbegrenzt
w = intAttr(child,"logicalScreenWidth",-1);           // aus dem GIF-Header
h = intAttr(child,"logicalScreenHeight",-1);
for (int i=0; i<v3; i++) {
    BufferedImage img = reader.read(i);
    BufferedImage canvas = new BufferedImage(w, h, TYPE_INT_ARGB);  // keine Obergrenze
    …
}
if (frames.size() > 80) { /* erst NACH dem Einlesen auf 80 reduziert */ }
```

- Frame-Anzahl und „logische Bildfläche“ stammen aus der Datei und werden **vor** der Allokation nicht begrenzt. Ein GIF mit `65535×65535` logischer Fläche fordert ~17 GB, ein GIF mit zehntausenden Frames wächst unbegrenzt.
- Die Felder `MAX_FRAMES`/`MAX_FRAME_W` existieren zwar, wirken im Dekodierpfad aber nicht als Vorbedingung: Die Begrenzung auf 80 Frames bzw. 224 px Kantenlänge greift **erst nach** dem vollständigen Einlesen (Bytecode: Schleifenende = `getNumImages(true)`, Canvas = `new BufferedImage(logicalScreenWidth, logicalScreenHeight, …)`).
- Emotes werden **von Drittanbieter-CDNs** geladen (7TV/BTTV). Wer dort ein Emote mit einem in der Community gebräuchlichen Namen veröffentlicht, kann Clients treffen, die dieses Emote per Suche/Registry laden (`EmoteRegistry`, `EmotePicker`, `EmoteGlyphs`).
- Derselbe Baustein (`ImageIO.read`/`shrink`) wird für Clan-Logos und Web-Icons benutzt (dort mit 64 KB/2 MB Cap und PNG-Check – das begrenzt die Dateigröße, nicht die dekomprimierte Bildgröße).

**Auswirkung:** `OutOfMemoryError`/langwierige Dekodierung ⇒ Client-Absturz oder minutenlanger Freeze; auf großen Servern als „Client-Killer-Emote“ missbrauchbar.
**Empfehlung:** `MAX_FRAMES` und maximale Kantenlänge **vor** der Dekodierung prüfen (`getWidth/getHeight` ohne `read()`), `ImageReadParam.setSourceSubsampling`, harte Byte-/Pixel-Budgets, Dekodierung in separatem Thread mit Timeout.

---

### F-05 – HTTP-Antworten werden unbegrenzt in den Speicher geladen
**Schwere: Mittel** · *CWE-400* · `net/friendswithmoney/fwm/net/FwmApi.java:36,60,188–203` · Beleg: `evidence/FwmApi.http.txt`

- Alle Requests nutzen `BodyHandlers.ofByteArray()/ofString()`, also **vollständiges Buffern** der Antwort.
- Der Parameter `maxBytes` wird erst **nach** dem Laden geprüft (`if (body.length > maxBytes) body = null;`) – er schützt also nicht vor der Allokation.
- `getUrl()`, `getBytes()`, `post()` haben überhaupt kein Limit; `getUrlStreamed()` wickelt die Antwort in `GZIPInputStream` und übergibt sie an einen JSON-Parser (Dekompressionsbomben).
- Betroffen sind sowohl Hersteller- als auch Drittanbieter-Endpunkte (7TV/BTTV/Mojang).

**Auswirkung:** Ein fehlerhaftes/kompromittiertes Backend oder ein manipulierter DNS-/Proxy-Pfad kann den Client per riesiger Antwort (oder GZIP-Bombe) aus dem Speicher werfen.
**Empfehlung:** `BodyHandler` mit hartem Limit (z. B. `ofInputStream` + `LimitedInputStream`), Limits vor dem Lesen durchsetzen, `Content-Length`/`Content-Encoding` prüfen, Timeouts beibehalten.

---

### F-06 – API-Key im Klartext, ohne Dateirechte-Härtung
**Schwere: Niedrig** · *CWE-256/312* · `net/friendswithmoney/fwm/net/ModAccount.java:13,32` · Beleg: `evidence/ModAccount.key.txt`

- Key-Format `fwm_[A-Za-z0-9_-]{16,200}` (per Regex validiert), Ablage in `config/fwm-key.json` als JSON-Klartext.
- Keine `PosixFilePermissions`/`AclFileAttributeView` – auf Mehrbenutzer-Systemen je nach `umask` für andere lokale Konten lesbar; Key landet außerdem in Backups/Cloud-Sync von `.minecraft`.
- Positiv: Der Key wird **ausschließlich** an die hartkodierte `BOT_API` (`https://api.friendswithmoney.net`) als `Authorization: Bearer …` gesendet, **nicht** an die frei konfigurierbare `apiBaseUrl`. Ein manipuliertes `fwm.json` kann den Key also nicht umleiten.
- Positiv: kein Logging des Keys (nur `hint()` = letzte 4 Zeichen im UI).

**Auswirkung:** Wer den Key erbeutet, kann im Namen des Nutzers Tickets einsehen/erstellen (API-Identität), nicht das Minecraft-Konto übernehmen.
**Empfehlung:** Datei mit `600` anlegen, Key widerrufbar machen, Rotation, im UI „Key kopieren“ nur maskiert, optional OS-Keychain.

---

### F-07 – `Authorization`-Header wird bei Redirects an fremde Hosts mitgeschickt
**Schwere: Niedrig (Defense-in-Depth)** · *CWE-200* · `FwmApi.java:217`

```java
CLIENT = HttpClient.newBuilder()…followRedirects(HttpClient.Redirect.NORMAL)…build();
```

Der JDK-`HttpClient` kopiert bei einer Weiterleitung **alle User-Header** in den neuen Request, auch bei Host-Wechsel (`RedirectFilter` → `HttpRequestImpl.newInstanceForRedirection` → `this.userHeaders = other.userHeaders`, geprüft gegen OpenJDK 21+35). `NORMAL` erlaubt Weiterleitungen auf andere Hosts (jedes `https`-Ziel). Ein offener Redirect auf der API (oder ein kompromittierter Endpunkt) würde den Bearer-Key an den Zielhost weiterreichen.

**Auswirkung:** Token-Leak nur bei kontrollierter Serverantwort/MITM-Fähigkeit – deshalb „Niedrig“, aber leicht zu beheben.
**Empfehlung:** `Redirect.NEVER` für authentifizierte Requests oder Redirect manuell mit Host-Vergleich; alternativ Key nur per Custom-Header an exakt einen Host.

---

### F-08 – „HugoSMP“-Erkennung per Substring
**Schwere: Niedrig** · *CWE-290* · `net/friendswithmoney/fwm/net/Presence.java` (`onHugo`)

```java
if (client.getCurrentServer().ip.toLowerCase().contains("hugosmp")) …
```

Jede Adresse wie `hugosmp.example.org`, `play.hugosmp-cheat.net` oder ein lokaler Eintrag mit diesem Namen aktiviert Presence-, ModId- und Badge-Funktionen – inklusive der oben genannten UUID-Uploads. Umgekehrt bleiben die Features auf einem echten HugoSMP aus, wenn der Nutzer den Server unter einem Alias/Proxy-Namen einträgt.
**Empfehlung:** Exakte Host-Allowlist (z. B. `*.hugosmp.de`) statt Substring; Feature-Gate zusätzlich an Opt-in koppeln.

---

### F-09 – Rekursives Löschen im Ressourcenpack-Ordner
**Schwere: Niedrig (Datenverlust)** · `net/friendswithmoney/fwm/antileak/AntiLeakPack.java:17,52–62`, `AntiLeak.java:82`

Beim Deaktivieren von „Anti-Leak“ löscht der Mod den kompletten Ordner `resourcepacks/FWM Anti-Leak` inklusive Unterordnern (`Files.walk(...).sorted(reverseOrder())` + `deleteIfExists`), und beim Aktivieren wird er zuvor geleert. Legt ein Nutzer dort eigene Dateien ab (Name ist generisch), sind sie weg. `TextureMixer.deleteTree` ist dagegen vorbildlich auf die Namen `FWM Mix`/`.fwm-mix-tmp` beschränkt.
**Empfehlung:** Nur selbst erzeugte Dateien löschen (Manifest), Ordner nicht rekursiv blind entfernen.

---

### F-10 – Automatische Spielaktionen (Chat-Klicks, `/tpaccept`)
**Schwere: Niedrig (Verhaltensrisiko)** · `chat/SecondChatClicks.java:103`, `game/AutoTpa.java:75`

- Der Zweit-Chat führt `ClickEvent.RunCommand`-Klicks aus Chat-Nachrichten **ohne Rückfrage** aus (`sendCommand(cmd)`), nachdem nur ein führendes `/` entfernt wurde. Das entspricht dem Vanilla-Verhalten, umgeht aber die Vanilla-Warnungen/Tooltips des Haupt-Chats – ein Server oder ein Spieler kann so „Klick-mich“-Befehle in einer alternativ gerenderten Chatzeile platzieren.
- `AutoTpa` akzeptiert Teleport-Anfragen automatisch (`/tpaccept`) – der Befehl wird aus dem Klick-Event der Server-Nachricht gelesen, aber **streng gegen `^/?tpaccept(…)?$` validiert** und nur für Namen aus der eigenen Allowlist ausgeführt (gut). Verbleibendes Risiko ist spielerisch: automatisches Annehmen von TPAs in Fallen.
- `Messages.send()` baut `msg <partner> <text>`; `<partner>` stammt aus Regex-Captures (`[A-Za-z0-9_]{3,16}`) → **keine** Command-Injection möglich. Ebenso sind alle fernbezogenen Namen auf `[A-Za-z0-9_]` begrenzt.

**Empfehlung:** Bestätigungsdialog für fremde Run-Command-Klicks im Zweit-Chat (oder Klicks dort nur für Suggest-Command zulassen), AutoTpa standardmäßig aus + Hinweis im UI.

---

### F-11 – Trust-/Identitätsanzeigen hängen an nicht authentisierten Serverdaten
**Schwere: Informativ–Niedrig** · *CWE-345* · `net/FakeList.java`, `net/TrustedClans.java`, `chat/FakeTags.java`, `chat/RealNames.java`, `net/Presence.java`

Der Mod blendet Badges/Logos und „Trusted“-Kennzeichnungen ein, die ausschließlich aus HTTP-JSON des Herstellers stammen (keine Signatur, kein Pinning). Ein kompromittierter Endpunkt könnte beliebige Spieler als „FWM Trusted“ markieren oder Namen umschreiben – genau die Anzeige, die Nutzer beim Handeln („Spawnerkauf über /msg“) als Vertrauenssignal nutzen. Die angezeigten Namen werden zwar aus dem echten Chattext gezogen, die Zuordnung Name→UUID jedoch über die API (`NAME_IDS`).
**Empfehlung:** Trust-Badges signieren (Ed25519) oder im UI klar als „Serverangabe, nicht verifiziert“ kennzeichnen; keine Handlungsempfehlungen („kauft hier“) an nicht signierte Daten knüpfen.

---

### F-12 – Konfigurations-URLs ohne Validierung
**Schwere: Informativ** · `config/FwmConfig.java:65,344–361`, `ConfigManager.load()`

`apiBaseUrl`, `membersApiUrl`, `marketApiUrl`, `rtpApiUrl`, `marketHistoryUrl` usw. werden unverändert aus `fwm.json` übernommen (nur `clamp()` für Zahlen). Damit lassen sich Marktpreise, Waypoints, „News“ und Clan-Listen auf einen Angreiferhost umbiegen (Wirtschafts-/Social-Engineering-Angriff, z. B. via geteilte Config). Der API-Key ist davon **nicht** betroffen (siehe F-06).
**Empfehlung:** URL-Felder auf `https` + Host-Allowlist prüfen oder als „advanced/experimental“ kennzeichnen; beim Laden warnen, wenn Hosts vom Default abweichen.

### F-13 – Badge-/Trust-Vergabe fällt auf Anzeigenamen zurück (Identitäts-Spoofing)
**Schwere: Mittel** · *CWE-290/CWE-345* · `net/friendswithmoney/fwm/net/Presence.java:117–198, 456–482`

```java
// decorateTabNow(uuid, name)
if (FWM.contains(uuid)) return name.copy().append(rowLogo());     // 1) echte Profil-UUID  ✅
String text = ServerLine.strip(name.getString());
String n    = rowName(text);                                      // 2) Anzeigename aus der Tab-Zeile
UUID   u2   = NAME_IDS.get(n.toLowerCase(Locale.ROOT));           //    Name→UUID via Mojang-Bulk-Lookup
if (u2 != null && FWM.contains(u2)) return name.copy().append(rowLogo());   // ← Name entscheidet
// 3) zusätzlich: Substring-Suche des Namens eines gerade online befindlichen Mitglieds im Text
```

- Primär ist die Prüfung korrekt UUID-basiert. **Fällt sie negativ aus, entscheidet der sichtbare Text:** Der erste Namens-Token der Tab-Zeile wird über `https://api.minecraftservices.com/…/lookup/bulk/byname` zur **Premium-UUID** aufgelöst und erneut gegen die FWM-Mitgliederliste geprüft. Zusätzlich genügt es, wenn der Anzeigetext den Namen eines *gerade online befindlichen* Mitglieds als ganzes Wort enthält.
- Wer seinen **Anzeigenamen** auf einen Mitgliedsnamen bringt (Nickname-Plugin, `/nick`, Offline-Mode-Name, serverseitig gesetzter Tab-Text), erhält also das FWM-Logo **ohne Mitglied zu sein**.
- Betroffen ist nur die **Anzeige** (Chat-Badge, Tab-Logo, Nametag-Icon) und damit das Vertrauenssignal, mit dem in der Community gehandelt wird – **nicht** der `fwm_…`-Key, nicht die Ticket-API, nicht Serverrechte.
- Abgrenzung: `TrustedClans.playerLabel(UUID)` ist sauber UUID-basiert (`TrustedClans.java:401–418`); `FakeList`/`FakeTags` markieren Fälscher (name-/textbasiert, Daten aus der API).

**Gegenmaßnahmen:** Badge ausschließlich bei UUID-Match (oder wenn der Server ein signiertes/rollenbewertes Attribut liefert); Namens-Fallback entfernen; auf dem Server Nicknames sperren, die Mitgliedsnamen entsprechen; ggf. Badge im UI als „vom Client erkannt“ kennzeichnen.

---

## 4. Was geprüft und **nicht** gefunden wurde (Negativbefunde)

| Prüfpunkt | Ergebnis |
|---|---|
| Prozessausführung (`Runtime.exec`, `ProcessBuilder`, `ProcessHandle`) | nicht vorhanden |
| Dynamisches Nachladen von Code (`URLClassLoader`, `defineClass`, `Instrumentation`, `attach`) | nicht vorhanden |
| Skripting/Deserialisierung (`ScriptEngine`, `ObjectInputStream`, Jackson-Polymorphie) | nicht vorhanden; nur Gson mit **konkreten** Zielklassen (keine Typinformation im JSON → kein Gadget-Risiko) |
| Reflexion | nur gezielt: `Class.forName("gg.norisk…")` für optionale Integration mit dem „NoRisk“-Client (`ui/NoRisk.java:60 ff.`) und `Field.setInt` für Minecraft-Farbfelder (`ui/FwmPalette.java`) |
| Session-/Launcher-Token-Diebstahl | keine Zugriffe auf `accessToken`, `launcher_accounts.json`, `.minecraft/launcher`, `usercache.json` o. ä. |
| Hardware-Fingerprinting (HWID, MAC, Hostname, Seriennummern) | nicht vorhanden; „ModId“ ist eine serverseitig vergebene Pseudonym-ID (`fwm_XXXXXXXX`) |
| Klartext-HTTP | keiner der Endpunkte |
| Hartkodierte Secrets/Webhooks | keine gefunden (Regex-Scan über 46.646 Strings) |
| TLS-Härtung | Standard-`HttpClient` ⇒ Standard-TrustStore + Hostname-Verification; **kein** eigener `SSLContext`, `TrustManager` oder `HostnameVerifier` |
| Pfad-Traversal beim Schreiben | `TextureMixer.safe()` prüft `normalize().startsWith(base)` und `..`; Clan-Logo-IDs per Regex `[a-z0-9_-]{2,24}|p_[0-9a-f]{32}`; Cache-Dateinamen aus UUID/Enums |
| Zip-Slip | keine Zip-Extraktion im Mod |
| Chat-Component-Injection (Klick-Events aus JSON) | kein `Component.Serializer`; Remote-Texte werden als `Component.literal`/`append` gerendert |
| Command-Injection über Namen/IDs | Namen aus Chat strikt `[A-Za-z0-9_]{3,16}`; TPA-Befehl streng validiert |
| Ressourcen-Leaks | Streams/BufferedReader werden durchgängig geschlossen (try-with-resources-Muster erkennbar) |

Positiv hervorzuheben: Der Umgang mit dem API-Key (nur an fixe Bot-API, Regex-Validierung, kein Logging), die Containment-Checks im Texture-Mixer und der PNG-Magic-/Größen-Check bei Clan-Logos zeigen ein bewusstes Sicherheitsdenken.

---

## 5. Priorisierung

| Prio | Befund | Aufwand |
|---|---|---|
| 1 | **F-01** Telemetrie transparent machen / Opt-out, `ban/check` an Server binden | klein–mittel (Client + Backend/Datenschutztext) |
| 2 | **F-04/F-05** Limits vor Dekodierung/Body-Read (DoS-Härtung) | klein |
| 3 | **F-03** Logo-URLs auf `apiBaseUrl`-relative Pfade + Allowlist beschränken | klein |
| 4 | **F-06/F-07** Key-Datei `600`, Redirects für authentifizierte Calls abschalten | sehr klein |
| 5 | **F-02/F-08** Sperr-System dokumentieren, exakte Host-Allowlist | klein |
| 6 | **F-09/F-10/F-11/F-12** Datenverlust, Auto-Aktionen, Badge-Signaturen, Config-Validierung | mittel |
| 7 | **F-13** Badge nur per UUID vergeben (Namens-Fallback entfernen), Nicknames reservieren | klein |
| 8 | **F-14** Serverzeilen-Heuristik härten (Default deny, Templates, `/nick`-Bypass) | klein |

**Gesamturteil:** Der Mod ist **kein Malware-Fall**: kein Code-Nachladen, keine Prozessausführung, kein Konto-/Session-Diebstahl, keine Klartext-Verbindungen. Die relevanten Probleme sind (a) **Datenschutz** (stille UUID-/Nutzernamen-Übermittlung, Alt-Meldungen, fremde UUIDs), (b) **Verfügbarkeit** (DoS über Bilder/Antwortgrößen) und (c) **Härtung/Transparenz** (Klartext-Key, Redirects, Kill-Switch, Trust-Anzeigen). Für Server-Admins gilt: Der Mod ist clientseitig, erzeugt aber Makro-/Automatisierungsverhalten (Sell-Makro, Spawner-Makro, Auto-TPA, Auto-Klicks) – das ist ein Regel-/Fairness-Thema, kein Server-Sicherheitsrisiko.

---

## 6. Ingame-Angreifer: können Mitspieler Rechte erlangen?

Kurzantwort: **Nein – ein normaler Mitspieler kann sich über den Mod keine Rechte verschaffen.** Es gibt im gesamten Client keinen Pfad, auf dem Spieler-Eingaben (Chat, Namen, Item-Namen, Tab-Liste) zu Befehlen, Dateizugriffen, API-Aufrufen oder „Trust"-Einstufungen werden. Was bleibt, sind **Täuschungs-/Automatik-Risiken** und ein **konfigurationsabhängiger Bypass** der Server-Nachrichten-Erkennung.

### 6.1 Was strukturell ausgeschlossen ist

| Frage | Befund |
|---|---|
| Kann ein Spieler einen Befehl auf meinem Client auslösen? | Nur über **Klick-Events aus Chat-Komponenten**, die der *Server/das Plugin* erzeugt – nicht durch reinen Chattext. Alle `sendCommand`-Senken (`AutoTpa`, `Messages`, `StreamerStore`, `SellController`, `SecondChatClicks`) sind geprüft: Spielernamen/-texte werden nie in Befehle interpoliert (Namen sind auf `[A-Za-z0-9_]{3,16}` begrenzt, der TPA-Befehl auf `^/?tpaccept(…)$`). |
| Kann ein Spieler meinen API-Key / „Mod-Account" übernehmen? | Nein. `fwm-key.json` wird nur beim Start gelesen, nur an die hartkodierte Bot-API gesendet; kein Chat-/Paketpfad liest oder schickt ihn. |
| Kann ein Spieler „Trusted"/Clan-/FWM-Badges vergeben? | Nein. Badges kommen aus `api.friendswithmoney.net` (Clan-/Fake-Listen) bzw. werden über Mojang-UUID-Lookups zugeordnet (`Presence.decorateTab` → UUID, nicht Name). `FakeTags` markiert *gefälschte* Clan-Tags sogar rot/durchgestrichen. |
| Kann ein Spieler meinen Client crashen? | Nicht direkt über Chat (Regexe ohne katastrophales Backtracking), aber indirekt über Dritt-Emotes (F-04) – ohne Spielerbezug. |
| Kann ein Spieler Dateien schreiben/löschen? | Nein – Dateinamen stammen aus UUIDs, Enums, festen Strings; IDs sind regex-validiert. |

### 6.2 Der einzige echte Bypass: Serverzeilen-Heuristik (F-14)
**Schwere: Mittel (konfigurationsabhängig)** · `net/friendswithmoney/fwm/chat/ServerLine.java` · Beleg: `tools/listing.py … ServerLine tagged startsColoured`

Alle Chat-Parser (Geld/Auktion/TPA/PMs/Sell-Statistik) hängen an einer einzigen Prüfung:

```java
fromServer(msg) = startsColoured(msg, cfg.serverPrefix /*Default "[HugoSMP]"*/)
                  && !(rest.matches("^\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:")        // NAME_COLON
                       && !SERVER_WORDS.contains(name.toLowerCase()));            // Ausnahmen
// SERVER_WORDS = { gedroppt, geliefert, achtung, abgeholt, verwendung, sellmulti }
```

- `startsColoured` verlangt, dass die Zeile **mit dem konfigurierten Präfix beginnt** und dessen Buchstaben **eingefärbt** sind (Server-typisch) – reiner Spielertext kann das nicht, weil die Zeile mit Rang/Name beginnt.
- **Aber:** Beginnt auf dem Server auch der normale Spieler-Chat mit `[HugoSMP] …` (das ist bei einem globalen Server-Tag üblich), dann ist die **Sechs-Wörter-Liste die einzige Barriere**. `NAME_COLON` prüft nur den ersten Token nach dem Präfix.
- Konsequenz: Ein Spieler, dessen **Nickname** (z. B. via `/nick`) exakt `Achtung`, `Gedroppt`, `Geliefert` … lautet, erzeugt eine Zeile `[HugoSMP] Achtung: …`, die als **Serverzeile** durchgeht. Damit sind Fälschungen möglich, u. a.:
  - `Achtung: Du hast $1.000.000 von Notch erhalten` → `MoneyEvents` (`matcher.find()` + `fromServer`) bucht eine **Fake-Einnahme** in HUD/Verlauf,
  - gefälschte Auktions-/Gebots-Zeilen (`Auction.accept`), TPA-Zustände (`TpaWatch`), Verkaufsstatistiken (`SellStats`).
- Praktische Folge: Betrugsmasche „schau, dein Overlay zeigt die Zahlung" bzw. falsche „Trusted"-Kommunikation.

**Gegenmaßnahmen**
1. Exakte Nachrichten-Templates statt `find()`-Substrings (Anker `^…$` nur bei vollständig bekannten Servertexten).
2. `SERVER_WORDS` → Whitelist kompletter Zeilenanfänge inkl. fester Rang-Präfixe; unbekannte `Wort:`-Zeilen grundsätzlich als Spielertext behandeln (Default deny statt Default allow).
3. Bei Geld-/Auktionsereignissen zusätzlich Server-API-Zustand (AH/Market) prüfen, bevor gebucht wird.
4. Test auf dem Server: (a) Beginnt eine normale Chat-Zeile roh mit `[HugoSMP]`? (b) Ist `/nick` erlaubt? (c) Sind die sechs Wörter als Nick frei? Wenn (a)=nein, ist der Bypass nicht anwendbar.

### 6.3 UUID manipulieren oder mit gecracktem Client unter gleichem Namen joinen?

| Szenario | Ergebnis |
|---|---|
| **Client schickt gefälschte UUID an die FWM-API** | Technisch trivial: `POST /api/mod/presence` (`Presence.java:365`) und `/presence/lookup` (:401), `/identify`, `/ban/check` nehmen `{uuid,name}` **ohne Credential** entgegen (Token-Parameter = `null`). Ein modifizierter Client (oder ein `curl`) kann beliebige Werte senden. Auswirkungen hängen rein am Backend (nicht prüfbar) – **diese Endpunkte dürfen serverseitig nie als Autorisierung dienen**. Für Badges anderer Spieler zählt dagegen die API-Antwort (Mitgliederliste), nicht der Client-Post. |
| **`fwm-id.json` (modId) fälschen** | Datei ist nur per Regex `fwm_[A-Z2-9]{8}` geprüft, also frei editierbar. Sie wird ausschließlich beim Giveaway-Join mitgesendet (`Giveaways.java:190`) – kein Rechtegewinn. |
| **Gecracktes Minecraft auf Online-Mode-Server (HugoSMP)** | Nicht möglich: Der Server verifiziert die Mojang-Session; Name/UUID sind nicht wählbar. Die UUID kommt vom Server, nicht vom Client. |
| **Gecracktes Minecraft auf Offline-Mode-Server** | Name frei wählbar, aber UUID = `MD5("OfflinePlayer:"+Name)` – **andere** UUID als der Premium-Account ⇒ UUID-Badge matcht nicht. **Aber:** über den Namens-Fallback (F-13) erscheint das FWM-Logo trotzdem, wenn der Anzeigename einem Mitglied entspricht. |
| **Nickname-Plugin auf HugoSMP** | Gleicher Effekt wie oben: Anzeigename = Mitgliedsname ⇒ Badge, obwohl die UUID nicht in der Liste steht. Praktisch der wahrscheinlichste Weg. |
| **Echte „Mod-Rechte“ (Tickets/`/mod/v1/*`)** | Nur mit dem lokalen `fwm_…`-Key (Bearer). Der ist an keinen Namen/UUID gebunden, liegt in `fwm-key.json` und ist über Ingame-Wege nicht erreichbar. |
| **Server-Ränge/Ops** | Ausschließlich Sache des Servers; der Mod sendet nie `/op` o. ä. |

**Für Serverbetreiber:** `online-mode=true` **und** korrektes IP-Forwarding (Velocity/BungeeCord mit Secret, keine offene `proxy-protocol`-Vertrauensstellung) – sonst ist Namens-Spoofing schon auf Serverebene möglich, unabhängig vom Mod.

### 6.4 Weitere spielernahe Vektoren (kein Rechte-Gewinn, aber Missbrauch)

| Vektor | Bewertung |
|---|---|
| **Klick-Befehl im Zweit-Chat** (`SecondChatClicks.run` → `sendCommand`): führt `RunCommand`-Klick-Events ohne Rückfrage aus. Vanilla-Spielerchat kann keine Klick-Events tragen; auf Servern mit Chat-/JSON-Plugins kann ein Spieler aber „Klick hier für Belohnung" bauen → ein Klick = `/pay …`. URLs werden vom Mod **nie** geöffnet (kein `OpenUrl` im Code) – Phishing-Links sind also kein Mod-Problem. | Niedrig; Bestätigungsdialog empfohlen |
| **Sell-Makro klickt in jedem Container** (`SellController.sellScreen()` prüft nur „nicht Inventar", **keinen Titel**) → wird während des Makros ein fremdes GUI geöffnet (Trade/Shop/Plugin-GUI), klickt der Mod dort auf `sellButtonSlot`. `SpawnerMacro` prüft dagegen den Titel auf „spawner". | Niedrig (braucht serverseitige Aktion); Titelprüfung ergänzen |
| **Giveaway-Teilnahme per PM** (`Giveaway.accept` ohne `fromServer`, aber mit striktem PM-Parser): Wer das Codewort kennt, kann sich eintragen – by design. | Info |
| **PM-Absender** ist **nicht** fälschbar: `Messages.parse` nutzt einen vollständigen Regex-Match (`matches()`) auf `[Nachricht] A → B: Text`, der Absender ist die erste Gruppe. Nur der **Text** ist frei wählbar (wie in jedem Chat). | OK |
| **Auto-TPA** akzeptiert nur Namen aus der eigenen Allowlist und validiert das Kommando per Regex; Wildcards greifen nicht. | OK |
| **Server (nicht Spieler)** kann alles oben jederzeit selbst: Er erzeugt Styling, Klick-Events und beliebige Zeilen. Der Mod vertraut dem Server-Output – das ist bei einem Client-Mod ohne Signaturprüfung unvermeidbar, sollte aber bewusst sein. | Design-Hinweis |

### 6.5 Fazit für die Praxis

- **Rechte-Eskalation (Ops/Server-Ränge/Mod-Key): nicht möglich.**
- **Realistisch:** Ein Mitspieler kann – bei passendem Chat-Format und `/nick` – **Systemmeldungen fälschen** und damit dein Geld-/Auktions-Overlay täuschen, oder dich per präpariertem Klick-Event zu einem Befehl verleiten. Beides ist Social Engineering mit einem zusätzlichen Klick bzw. einer Server-Fehlkonfiguration, kein technischer Rechte-Gewinn.
- Wer den Mod auf einem **fremden** Server nutzt (statt HugoSMP), sollte `serverPrefix` leer lassen bzw. die Parser-Features abschalten – dann sind die Chat-Parser wirkungslos.

---

## 7. Anhang

### 7.1 Dateien in diesem Repo

```
fwm-1.10.6.6_MC-26.3.jar     Original-Artefakt (unverändert)
decompiled/                  382 rekonstruierte Pseudo-Java-Dateien (Paketstruktur wie im Jar)
evidence/                    Bytecode-Listings der sicherheitsrelevanten Methoden
tools/                       Decompiler + Analyseskripte (Python 3, Abhängigkeit: jawa)
SECURITY-REVIEW.md           dieser Bericht
```

### 7.2 Reproduktion

```bash
pip install jawa
mkdir -p x && (cd x && unzip -o ../fwm-1.10.6.6_MC-26.3.jar)
python3 tools/run_dec.py                       # -> decompiled/
python3 tools/listing.py x net/friendswithmoney/fwm/net/ModBan tick
python3 tools/dump_strings.py x strings.json   # Konstantenpool-Scan
python3 tools/dump_refs.py x refs.json         # API-Cross-References
```

Beispielabfragen für die Befunde:

```bash
grep -rn "Runtime.getRuntime\|ProcessBuilder\|URLClassLoader\|ScriptEngine" decompiled/   # -> leer
grep -rn "followRedirects\|Authorization" decompiled/net/friendswithmoney/fwm/net/FwmApi.java
grep -rn "ban/check\|presence/lookup\|identify" decompiled/ | head
```

### 7.3 Grenzen

- Pseudocode-Decompiler: 28 von 3.968 Methoden mit Code nicht rekonstruierbar (überwiegend UI-Rendering/Enum-Initialisierung); diese wurden bei Bedarf im Bytecode geprüft, können aber theoretisch Details verbergen.
- Keine Laufzeitanalyse, kein Traffic-Mitschnitt, kein Fuzzing der Bild-/JSON-Parser.
- Backend (`269153435.de`, `api.friendswithmoney.net`) wurde **nicht** geprüft – dortige Schwachstellen (offene Redirects, AuthZ-Fehler, Datenabfluss) sind aus dem Client-Jar nicht beurteilbar.
- Keine Aussage über die Vertrauenswürdigkeit des Anbieters, nur über das technische Verhalten des Clients.
