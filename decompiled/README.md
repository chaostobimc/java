# FWM – dekompilierte Analysefassung

Diese Dateien wurden aus `fwm-1.10.6.6_MC-26.3.jar` mit CFR 0.152 rekonstruiert. Die Original-JAR enthält laut `fabric.mod.json` eine MIT-Lizenz; siehe `LICENSE`.

Dies ist **kein fertiges Build-Projekt** und keine modifizierte Mod-JAR. Dekompilierter Code kann fehlerhaft oder unvollständig sein (insbesondere komplexer Kontrollfluss, Lambdas und innere Klassen); mindestens einige Dateien enthalten entsprechende CFR-Hinweise. Die Ressourcen und Mod-Metadaten wurden unter `resources/` aus der JAR extrahiert. Abhängigkeiten und Gradle-Konfiguration sind nicht eingerichtet. Die Dateien dienen zunächst der Untersuchung und müssen vor einem Build geprüft und angepasst werden.

Die Ausgangs-JAR bezeichnet sich bereits als clientseitige Fabric-Mod für Minecraft `~26.3`. Das behebt für sich genommen weder den gemeldeten Netzwerkfehler noch beinhaltet es ein neues Design. Zur Diagnose des Verbindungsfehlers werden `logs/latest.log` und gegebenenfalls der zugehörige Bericht aus `debug/` unmittelbar nach dem fehlgeschlagenen Beitritt benötigt. Bitte Zugangsdaten und persönliche Daten vor dem Teilen entfernen.

Ein experimenteller, separat gebauter Keyboard-/Theme-Patch liegt unter `../patch/`; Hinweise zur erzeugten JAR und zu den Grenzen der Tests stehen in `../patch/README.md`. Diese Analysefassung selbst ist weiterhin kein Build-Projekt.
