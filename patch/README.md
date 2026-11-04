# FWM 26.3 – experimenteller Clean-/Keyboard-Patch

Die neue JAR baut auf der mitgelieferten Original-JAR auf. Sie ersetzt das zentrale `FwmTheme` durch ein reduziertes, flaches Farbschema und ergänzt einen Mixin-Schutz vor ungültigen SDL-Tastaturindizes in `InputConstants.isKeyDown(int)`. Andere Klassen und Ressourcen bleiben unverändert; es ist kein vollständiges Redesign aller Ansichten. Die Abhängigkeit ist auf Minecraft **26.3** beschränkt.

Der Guard gibt nur für negative Indizes, Indizes außerhalb der tatsächlichen SDL-Buffer-Grenze oder einen nicht verfügbaren Buffer `false` zurück. Reguläre Tastenabfragen laufen unverändert durch. Damit soll der im Log gezeigte Absturz beim Schließen eines Containers vermieden werden; die tatsächlich fehlerhafte Tastenzuordnung ist noch nicht identifiziert. Der Guard wirkt global, also auch bei Abfragen anderer Mods. Er schluckt keine Netzwerkpakete und fängt keine allgemeinen Exceptions ab.

## Installation und Test

1. Original-JAR im OneClient-Modordner deaktivieren oder entfernen (aufbewahren). **Nicht beide FWM-JARs gleichzeitig installieren.**
2. `fwm-1.10.6.6-clean-fix_MC-26.3.jar` installieren und den Client vollständig neu starten.
3. Serverbeitritt testen und anschließend Tasten, Menü und Funktionen prüfen. Vorher bei Bedarf `options.txt` und `config/` sichern.
4. Bei einem Fehler Original wiederherstellen und `latest.log` sowie gegebenenfalls den Disconnect-Bericht aus `debug/` teilen; persönliche Daten und Tokens entfernen.

**Status:** Der Bounds-Guard wurde isoliert mit simuliertem SDL-Buffer auf Grenzwerte getestet und die JAR strukturell geprüft. Kein Minecraft-/OneClient-Lauf und kein Multiplayer-Test waren in dieser Umgebung möglich. Die Mod ist daher eine Testversion, kein bestätigter Fix.

## Reproduzieren

Python 3 und ein JDK mit `javac` werden benötigt. Im Repository-Hauptverzeichnis `python patch/build.py` ausführen. Das Skript prüft den SHA-256 der Original-JAR und erzeugt die neue JAR. `patch/stubs` enthält ausschließlich Compile-Stubs für die externen Minecraft-/Mixin-/LWJGL-APIs; diese Stubs werden **nicht** in die JAR aufgenommen. Der restliche dekompilierte Code unter `decompiled/` ist eine Analysefassung und kein vollständig wiederherstellbares Gradle-Projekt.
