# Analyse-Werkzeuge (FWM-Mod-Review)

Alle Skripte sind Python 3 und benötigen nur `jawa` (`pip install jawa`).
Eingabe ist ein entpacktes Jar-Verzeichnis (`x/`), z. B.:

```bash
mkdir -p x && (cd x && unzip -o ../fwm-1.10.6.6_MC-26.3.jar)
```

| Skript | Aufruf | Ausgabe |
|---|---|---|
| `run_dec.py` | `python3 tools/run_dec.py` | schreibt `decompiled/<klasse>.java` für alle Klassen (Pfade `x/`, `decompiled/` sind im Skript hinterlegt) |
| `dec.py` | Bibliothek bzw. `python3 tools/dec.py x <interner/klassenname> out.java` | Pseudo-Java einer Klasse |
| `listing.py` | `python3 tools/listing.py x net/friendswithmoney/fwm/net/ModBan tick` | javap-artiges Bytecode-Listing mit aufgelöstem Konstantenpool, optional nur bestimmte Methoden |
| `dump_strings.py` | `python3 tools/dump_strings.py x strings.json` | alle UTF-8-Konstanten je Klasse (Secret-/Endpunkt-Suche) |
| `dump_refs.py` | `python3 tools/dump_refs.py x refs.json` | alle Methoden-/Feldreferenzen je Klasse (Suche nach gefährlichen APIs) |

Hinweise:

- Der Decompiler simuliert den Operanden-Stack und löst `invokedynamic`-String-Concats über die `BootstrapMethods`-Attribute auf. Er erzeugt **lesbaren Pseudocode mit Gotos**, keinen compiling-fähigen Java-Code.
- Verzweigungen werden als `if (…) goto Lxx;` ausgegeben; Sprungmarken entsprechen Bytecode-Offsets.
- Für Aussagen im Bericht wurden die sicherheitsrelevanten Methoden zusätzlich als Bytecode-Listing in `evidence/` abgelegt und gegengelesen.
- Typische Nützlichkeits-Flags: `mn=='lookupswitch'`/`tableswitch` werden nur kommentiert, Booleans erscheinen als `0/1`.
