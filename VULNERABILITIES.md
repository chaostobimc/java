# Sicherheitslücken – vollständige Liste

**Artefakt:** `fwm-1.10.6.6_MC-26.3.jar` (FWM Mod 1.10.6.6, Fabric, Minecraft 26.3, 1,53 MB)
**Stand:** 31.10.2026 · **Basis:** `decompiled/` (382 Klassen), `evidence/` (10 Bytecode-Listings)
**Detailbericht:** `SECURITY-REVIEW.md` (Abschnitte 1–8) · **Diese Datei:** konsolidierte Gesamtliste + API-Angriffsfläche

---

## 0. Direkte Antwort auf die Frage

> „Gibt es ungesicherte API-Schnittstellen – können z. B. Spieler ohne Berechtigung andere Spieler bannen?“

**Ja, es gibt ungesicherte Schnittstellen; nein, es gibt keinen Endpunkt, der direkt einen anderen Spieler bannt.**

| Teilfrage | Antwort |
|---|---|
| Gibt es Endpunkte ohne Authentifizierung? | **Ja.** Nur **5** Endpunkte verlangen überhaupt ein Credential (alle `/mod/v1/*`, `Authorization: Bearer <fwm-key>`). **Alle anderen – 15 Pfade auf `269153435.de`, 2 auf `api.friendswithmoney.net` und 3 konfigurierbare Daten-URLs – sind ohne Key, Token oder Signatur erreichbar.** Darunter **6 POST-Endpunkte**, die Daten schreiben. |
| Kann ein Unbeteiligter einen anderen Spieler bannen? | **Nicht direkt.** Es gibt keinen „ban user X“-Aufruf. `banned` ist ausschließlich die Antwort des Backends auf `/api/mod/ban/check` für die **eigene** UUID des Aufrufers. Die Sperrentscheidung fällt zu 100 % im Backend. |
| Kann er es *indirekt*? | **Backend-abhängig, plausibel.** `/api/mod/ban/alt` nimmt **unauthentifiziert** beliebige `{uuid, name, of, ofUuid}`-Paare entgegen und meldet sie als Zweitaccount eines Gesperrten. Setzt das Backend solche Reports automatisch in Sperren/Flags um, kann jeder beliebige Accounts anschwärzen. Aus dem Client ist nicht erkennbar, ob das passiert → **muss serverseitig geprüft werden** (siehe § 7). |
| Kann er *ohne* Backend jemanden aussperren? | **Ja, lokal:** `fwm-sperre.json` anlegen **oder beschädigen** sperrt die Installation (fail-closed, F-15). **Ja, remote:** Wer die TLS-Strecke kontrolliert (Proxy mit eigener CA, kompromittiertes Backend), kann `{"banned":true}` liefern – die Antwort ist **nicht signiert** und wird ungeprüft übernommen. |
| Betrifft das Serverrechte, Mod-Key, Tickets? | **Nein.** Die Sperre deaktiviert ausschließlich Mod-Funktionen; Tickets/`fwm_…`-Key bleiben unberührt. |

---

## 1. Legende

| Spalte | Bedeutung |
|---|---|
| **Schwere** | Kritisch / Hoch / Mittel / Niedrig / Informativ – bezogen auf **Nutzer des Mods**, nicht auf den MC-Server |
| **Belegt** | mit Code-Zeile und/oder Bytecode-Listing in `evidence/` nachgewiesen |
| **Backend-abhängig** | Client-Verhalten belegt, Wirkung hängt von nicht einsehbarem Servercode ab |

Gezählt: **17 Einzelbefunde** (F-01 … F-17), davon 15 in `SECURITY-REVIEW.md` dokumentiert, **F-16/F-17 neu** in dieser Datei.

---

## 2. Vollständige Befundliste

| ID | Befund | Schwere | Kategorie | Ort / Beleg | Kernaussage |
|---|---|---|---|---|---|
| **F-01** | Stille Übermittlung von UUID + Name (Telemetrie ohne Opt-out) | Mittel | Datenschutz | `ModBan.java:81,93,113`, `Presence.java:353–401` | `/ban/check` alle 30 min, `/presence` alle 5 min – ohne Schalter, kein Opt-out |
| **F-02** | Serverseitiger Kill-Switch deaktiviert den Mod | Mittel | Verfügbarkeit | `ModBan.java:113`, `Features.java:40`, `Design.java:9` | Backend kann per `banned=true` jede Installation abschalten |
| **F-03** | Blinde SSRF über API-gelieferte Bild-URL | Mittel | SSRF | `ClanLogos.java:128,149` · `evidence/ClanLogos.fetch.txt` | `logo`-Feld wird ungeprüft per GET geladen (beliebiger Host, 2 MB Cap) |
| **F-04** | Ungebremste GIF-/Bild-Dekodierung | Mittel | DoS | `EmoteTexture.java:221` · `evidence/EmoteTexture.readGif.txt` | Frame-/Pixel-Limits fehlen → Client-Freeze/OOM |
| **F-05** | HTTP-Antworten unbegrenzt gepuffert | Mittel | DoS | `FwmApi.java:36,60,188–203` · `evidence/FwmApi.http.txt` | `maxBytes` wird erst **nach** dem Lesen geprüft |
| **F-06** | Mod-Key im Klartext ohne Dateirechte | Niedrig | Credentials | `ModAccount.java:13,32` · `evidence/ModAccount.key.txt` | `fwm-key.json` world-readable; jeder lokale Prozess kann ihn lesen |
| **F-07** | `Authorization` folgt Redirects an fremde Hosts | Niedrig | Informationsleck | `FwmApi.java:217` | Bei 30x wird der Bearer-Key an den Redirect-Zielhost mitgeschickt |
| **F-08** | „HugoSMP“-Erkennung per Substring | Niedrig | Logikfehler | `Presence.java` (`onHugo`) | `ip.contains("hugosmp")` → fremder Server kann Presence/Identify auslösen |
| **F-09** | Rekursives Löschen im Ressourcenpack-Ordner | Niedrig | Datenverlust | `AntiLeakPack.java:17,52–62`, `AntiLeak.java:82` · `evidence/AntiLeakPack.delete.txt` | Löscht `resourcepacks/FWM Anti-Leak` rekursiv |
| **F-10** | Automatische Spielaktionen | Niedrig | Verhaltensrisiko | `SecondChatClicks.java:103`, `AutoTpa.java:75` · `evidence/AutoTpa.txt` | Klick-Befehle aus Chat, automatisches `/tpaccept` |
| **F-11** | Trust-Anzeigen aus nicht authentisierten Daten | Informativ–Niedrig | Integrität | `FakeList.java`, `TrustedClans.java`, `chat/FakeTags.java` | Badges/Fake-Markierungen kommen ungesichert aus der API |
| **F-12** | Konfigurations-URLs ohne Validierung | Informativ | Härtung | `FwmConfig.java:65,344–361` | `apiBaseUrl`, `membersApiUrl`, `marketApiUrl` … frei editierbar |
| **F-13** | Badge-Vergabe fällt auf Anzeigenamen zurück | Mittel | Identitäts-Spoofing | `Presence.java:117–198,456–482` | Wer sich wie ein Mitglied nennt, bekommt das FWM-Logo (`/nick`, Offline-Mode) |
| **F-14** | Umgehbare Serverzeilen-Heuristik | Mittel (konfig.-abh.) | Chat-Spoofing | `ServerLine.java` (siehe `SECURITY-REVIEW.md` § 6.2) | 6 Wörter + `[HugoSMP]`-Prefix entscheiden, was als „Server“ gilt |
| **F-15** | Sperr-System: fail-closed, unsigniert, installationweit | Mittel | Integrität/DoS | `ModBan.java:34–48,76–149`, `Design.java:8–14` · `evidence/ModBan.locked.txt`, `evidence/Design.open.txt` | Kaputte Datei = Bannscreen; Antwort ohne Signatur; Sperre gilt für alle Accounts am PC |
| **F-16** | **Unauthentifizierte Schreib-Endpunkte – Identitäten frei fälschbar** | **Mittel (backend-abhängig)** | AuthN/AuthZ | `ModBan.java:103,113`, `Presence.java:365,401`, `ModId.java:69`, `Giveaways.java:191` | 6 POST-Endpunkte ohne Credential; `uuid`/`name`/`modId`/`lockedBy` sind reine Behauptungen des Clients |
| **F-17** | **Mitgliedschafts-Orakel über `/presence/lookup` und Giveaway-Abfrage** | **Niedrig** | Datenschutz/Enumeration | `Presence.java:380–410`, `Giveaways.java:91` | 200 beliebige UUIDs pro Request → Liste der FWM-Mitglieder; `?uuid=` liefert fremde Giveaway-Teilnahmen |

---

## 3. API-Angriffsfläche (vollständige Endpunkt-Matrix)

### 3.1 Basis `https://269153435.de` (= `apiBaseUrl`, **konfigurierbar**, F-12)

| Methode + Pfad | Auth | Vom Client gesendete Daten | Wer kann aufrufen | Risiko |
|---|---|---|---|---|
| `POST /api/mod/ban/check` | **keine** | `{uuid, name, lockedBy}` | jeder (Mod oder `curl`) | Antwort steuert die **lokale Sperre**; unsigniert; `lockedBy` frei setzbar; Replay trivial (F-15/F-16) |
| `POST /api/mod/ban/alt` | **keine** | `{uuid, name, of, ofUuid}` | jeder | **Ban-relevant:** beliebige Accounts als „Alt von Gesperrtem“ meldbar → falsche Sperren, wenn das Backend Reports automatisch umsetzt (F-16) |
| `POST /api/mod/presence` | **keine** | `{uuid, name}` | jeder | Präsenz-/Badge-Datenbank fälschbar; nur wenn Server-IP `hugosmp` enthält (F-08) |
| `POST /api/mod/presence/lookup` | **keine** | `{uuids:[… ≤ 200]}` | jeder | **Mitgliedschafts-Orakel**: beliebige UUIDs auf FWM-Mitgliedschaft prüfen (F-17) |
| `POST /api/mod/identify` | **keine** | `{uuid, name, modId, modVersion}` | jeder | Installations-ID eines beliebigen Spielers behauptbar; die Antwort (`merged`) steuert die lokale `fwm-id.json` |
| `GET /api/mod/giveaways?uuid=<32hex>` | **keine** | UUID im Query | jeder | fremde Giveaway-Teilnahmen/Historie lesbar (F-17) |
| `POST /api/mod/giveaways/<id>/join` | **keine** | `{uuid, name, modId}` | jeder | Teilnahme/Teilnehmerzahl im Namen anderer fälschbar (F-16) |
| `GET /api/mod/clans` | keine | – | jeder | Trusted-Clan-Liste (Anzeige) – MITM kann Badges fälschen (F-11) |
| `GET /api/mod/fakes` | keine | – | jeder | Fake-Account-Liste (Anzeige) |
| `GET /api/mod/news` | keine | – | jeder | News-Texte im UI (Anzeige) |
| `GET /api/mod/waypoints` | keine | – | jeder | Waypoint-Daten (Anzeige, schreibt lokale Datei) |
| `GET /api/mod/rtp?dim=` | keine | Dimension | jeder | RTP-Punkt-Cloud (Anzeige) |
| `GET /api/mod/market/changes` · `/history?item=` · `/prices` | keine | Item-Keys | jeder | Markt-/Preisverlauf (`marketHistoryUrl`, Default `…/api/mod/market`) |
| `GET <clan logo>` (beliebige URL aus der API) | keine | – | jeder | **SSRF** im Client (F-03) |

### 3.2 Basis `https://api.friendswithmoney.net` (**hartcodiert**)

| Methode + Pfad | Auth | Daten | Risiko |
|---|---|---|---|
| `GET /mod/v1/key/check` | **Bearer `fwm_…`** | – | Key-Validierung; 401 ⇒ Key wird lokal gelöscht |
| `GET /mod/v1/tickets` | Bearer | – | Ticketliste |
| `GET /mod/v1/tickets/<id>/messages?limit=50&after=` | Bearer | – | Ticketverlauf |
| `POST /mod/v1/tickets` | Bearer | `{type, …}` | Ticket anlegen (Spawner-Kauf/Middleman) |
| `GET /mod/v1/spawners` | Bearer | – | Spawner-Preise |
| `GET /trusted-clans` | **keine** | ETag | Clan-Liste – MITM-/Backend-Einfluss auf Badges |
| `GET /fake-clans` | **keine** | ETag | Fake-Clan-Liste |
| `GET /assets/…` (Font-/Icon-Pfade) | keine | – | Bild-/Font-Download |

### 3.3 Konfigurierbare Daten-URLs (Defaults, alle ohne Auth)

| URL | Zweck | Risiko |
|---|---|---|
| `https://friendswithmoney.net/api/spawner-types` | Spawner-Preise | frei umbiegbar (F-12) |
| `https://friendswithmoney.net/api/clan-members` | Mitgliederliste/Badges | frei umbiegbar → **jeder Host kann Badges „verleihen“** |
| `https://friendswithmoney.net/api/marketplace` (`/overview`, `/items/<key>/stats`, `/live-orders`, `/live-auctions`) | Markt, Preispings | frei umbiegbar; Preis-Pings erzeugen Chatnachrichten |
| `https://api.minecraftservices.com/minecraft/profile/lookup/bulk/byname` | Name→UUID (Mojang) | nur Anzeige-Auflösung; Grundlage für F-13 |
| `https://api.7tv.app/v4/gql`, `https://api.betterttv.net/3/…` | Emotes | Drittanbieter-Telemetrie (Suchbegriffe, IP) |

### 3.4 Was auf Client-Seite **fehlt** (Ursache der meisten Befunde)

- **Keine Signatur** der Antworten – weder für Sperrentscheidungen noch für Clan-/Mitgliederlisten.
- **Keine Nonce/Zeitstempel/Request-Signatur** → Replay und Fälschung beliebiger Meldungen.
- **Kein Zertifikats-Pinning**, kein Host-Check bei Redirects (F-07).
- **Keine Bindung** von Antwort zu Anfrage (die zurückgegebene UUID/Name wird nicht gegen die eigene geprüft).
- **Kein Rate-Limit** (Ausnahmen: Clan-Logos 1/s, Preispings 2–20 s); unbegrenzte Retries.
- **Keine Server-Verifikation** der Identität: `uuid`/`name`/`modId` sind reine Client-Behauptungen.
- **Kein Opt-out** für Telemetrie/Ban-Check.

---

## 4. „Kann Spieler A Spieler B bannen?“ – Entscheidungsbaum

```
Will A, dass B gesperrt wird
│
├─ über die FWM-API?            → kein Endpunkt vorhanden ................. NEIN
├─ über gefälschte Alt-Meldung  → /api/mod/ban/alt (ohne Auth) ............ BACKEND-ABHÄNGIG
│     └─ wenn Backend Reports automatisch sperrt .......................... JA (dann möglich)
│     └─ wenn Reports nur manuell geprüft werden .......................... nur „Anschwärzen“
├─ über eine gefälschte Antwort → MITM/CA/kompromittiertes Backend ........ JA (lokal bei B)
├─ über die Sperrdatei          → Zugriff auf B's PC (oder korrupte Datei) . JA (lokal bei B)
├─ über den MC-Server           → Mod sendet nie Bann-Befehle ............. NEIN
└─ B wieder entsperren?         → Backend, MITM oder apiBaseUrl umbiegen ... JA (Umgehung)
```

**Fazit:** Die Sperre ist ein **clientseitiges Feature-Gate**, dessen Entscheidung das Backend trifft und die der Client **unüberprüft** übernimmt. Ein Fern-Ban ohne Backend ist nur über die TLS-Strecke möglich; ein *Anschwärzen* über `/ban/alt` ist dagegen ohne jedes Credential möglich.

---

## 5. Angriffsszenarien (Aufwand → Wirkung)

| # | Angreifer | Voraussetzung | Wirkung | Befunde |
|---|---|---|---|---|
| A | Mitspieler mit modifiziertem Client / `curl` | keine Credentials | Presence/Identify/Giveaway-Teilnahme für beliebige UUIDs fälschen; Alt-Meldungen gegen Dritte; eigene Badges über Namensgleichheit | F-13, F-16, F-17 |
| B | Mitspieler im Chat | keine | Serverzeilen spoofen → Klick-Befehle/Fake-Meldungen im Zweit-Chat | F-14, F-10 |
| C | Netzwerkangreifer mit vertrauenswürdigem Zertifikat (Proxy, Firmen-CA, gekaperte DNS+CA) | Position im Netz + CA | jede Installation sperren; Clan-/Mitglieder-/Preis-Daten fälschen; ggf. Key-Leak über Redirect | F-02, F-07, F-11, F-15 |
| D | Kompromittiertes/feindliches Backend | Kontrolle über API | Massen-Kill-Switch, SSRF im Client, rekursives Löschen, gefälschte Trust-Anzeigen | F-02, F-03, F-09, F-11 |
| E | Lokaler Angreifer (geteilter PC, anderes Programm) | Dateisystemzugriff | Key auslesen, Sperre setzen/entfernen, Config umbiegen | F-06, F-12, F-15 |
| F | Serverbetreiber | – | kein Sicherheitsrisiko, aber Automatisierung/Anti-Cheat-Regelverstoß | F-10 |

---

## 6. Negativbefunde (geprüft, **nicht** vorhanden)

| Geprüft | Ergebnis |
|---|---|
| Remote-Code-Ausführung, Classloading, `ScriptEngine` | **nicht vorhanden** – kein `URLClassLoader`, `defineClass`, `setAccessible`, `exec`/`ProcessBuilder` |
| Diebstahl von Minecraft-/Launcher-/Microsoft-Sessions | **nicht vorhanden** – kein Zugriff auf Launcher-Accounts, keine Session-Tokens, kein Refresh-Token |
| Hartcodierte Secrets/API-Keys im Jar | **keine** – nur `Bearer \x01`-Formatstring; der Key kommt ausschließlich aus `fwm-key.json` |
| Klartext-HTTP | **keine** – alle Default-URLs `https://` |
| Pfad-Traversal über Remote-Daten | **geschützt** – Clan-IDs gegen `[a-z0-9_-]{2,24}|p_[0-9a-f]{32}` geprüft, Dimensionen sind Enum, Wallet-IDs UUIDs, `TextureMixer` normalisiert + Prefix-Check |
| Zip-Slip / Entpacken fremder Archive | nicht vorhanden (kein Archiv-Entpacken) |
| Authentifizierte Endpunkte als Angreifer nutzen | Ticket-API nur mit gültigem `fwm_…`-Key; ohne Key kein Zugriff |
| Parse von signiertem Spielerchat | **nein** – nur unsignierte Game-Messages werden geparst |
| Hardware-Fingerprinting (HWID) | **nein** – nur UUID, Name, `modId`, `modVersion` |
| Serverrechte/`/op`/Plugins | Mod ist rein clientseitig |

---

## 7. Härtungs-Checkliste

### 7.1 Client (Mod-Autor)

1. **Antworten signieren** (Ed25519, Public Key im Jar) – mindestens für `/ban/check`, `/clans`, `/members`.
2. **Fail-open** bei defekter/fehlender Sperrdatei; Sperre an `note.uuid == selfUuid()` binden (F-15).
3. Sperr-Endpunkt **fest verdrahten**, nicht über `apiBaseUrl` (F-12).
4. Badge nur bei UUID-Match, Namens-Fallback entfernen (F-13).
5. Redirects für authentifizierte Requests deaktivieren bzw. Host-Allowlist (F-07).
6. Limits vor dem Lesen/Dekodieren (F-04/F-05), Key-Datei mit `600` (F-06).
7. Telemetrie (Presence/Ban-Check) dokumentieren und abschaltbar machen (F-01).

### 7.2 Backend (zwingend, weil die Endpunkte unauthentifiziert sind)

1. **Alle** `/api/mod/*`-Eingaben als nicht vertrauenswürdig behandeln: `uuid`/`name` sind Behauptungen.
2. Identität serverseitig verifizieren (Mojang-Session/Name↔UUID-Abgleich), nicht aus dem Body übernehmen.
3. `/ban/alt` **nie** als Beweis für Ban-Evasion werten – nur als Hinweis mit manueller Prüfung, Rate-Limit pro IP/UUID, Idempotenz, TTL.
4. Keine automatischen Sperren aus Client-Meldungen; Sperren nur aus nachvollziehbaren Quellen.
5. Rate-Limits + Replay-Schutz (Nonce/Timestamp) für alle POST-Endpunkte.
6. `/presence/lookup` authentifizieren oder stark limitieren (Mitglieder-Orakel, F-17).
7. Giveaway-Teilnahme serverseitig an verifizierte Identität binden (F-16).
8. `lockedBy` ignorieren (kommt vom Client) und nur serverseitig pflegen.

---

## 8. Reproduktion

```bash
# Sperr-Mechanik (Belege liegen in evidence/)
python3 tools/listing.py /tmp/x net/friendswithmoney/fwm/net/ModBan locked load
python3 tools/listing.py /tmp/x net/friendswithmoney/fwm/ui/Design open

# alle API-Aufrufe mit Authentifizierungsstatus
grep -rn "FwmApi\.\(get\|post\|request\|getUrl\)(" decompiled/
grep -rn "mod/v1\|api/mod" decompiled/ | sort

# Bestätigung: kein exec/Classloading/Secret im Jar
grep -rn "ProcessBuilder\|Runtime.getRuntime\|defineClass\|setAccessible" decompiled/   # leer
```

*Hinweis:* Die API-Hosts wurden **nicht** aktiv getestet (kein Zugriff aus der Sandbox, und Tests gegen fremde Systeme wären ohne Auftrag unzulässig). Alle Aussagen zur Serverseite sind als „backend-abhängig“ markiert.

---

## 9. Bezug zum Detailbericht

| Abschnitt in `SECURITY-REVIEW.md` | Inhalt |
|---|---|
| 1–2 | Vorgehen, Architektur, Vertrauensgrenzen |
| 3 | F-01 … F-13, F-15 im Detail (Code, Auswirkung, Gegenmaßnahme) |
| 4 | Negativbefunde |
| 5 | Priorisierung |
| 6 | Ingame-Angreifer (F-14, Serverzeilen-Heuristik; UUID-Spoofing) |
| 7 | Sperr-System im Detail (7.1–7.7) |
| 8 | Anhang: Dateien, Reproduktion, Grenzen |
