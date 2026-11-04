# Untersuchung des Disconnects

Im vom Nutzer bereitgestellten Log wird der Client nach dem Beitritt zu HUGOSMP getrennt. Der unmittelbar auslösende Fehler ist eine `IndexOutOfBoundsException` in `DirectByteBuffer.get`, aufgerufen von `InputConstants.isKeyDown` über `KeyMapping.setAll`, als die Maus nach einem `ClientboundContainerClosePacket` wieder eingefangen wird. Der Stack enthält dabei auch den `grabMouse`-Wrapper von Ixeris und den `setScreen`-Wrapper von PolyPlus. Ein FWM-Frame steht nicht im Stack; aus dem Log allein ist daher nicht belegbar, welche Tastenzuordnung oder Mod den ungültigen Index verursacht hat. Die Meldung „Network Protocol Error“ beschreibt hier die Folge einer Exception bei der Paketverarbeitung, nicht zwangsläufig ein fehlerhaftes Paket.

FWM registriert in `input/Keybinds.java` sechs Tastenzuordnungen, drei davon standardmäßig unbelegt. In `ui/widget/KeybindRow.java` können Tastatur- und Maustasten gespeichert werden. Diese Stellen sind relevante Prüfstellen, aber ein unbelegter Key allein belegt keinen Fehler. Auch andere Mods registrieren Tastenzuordnungen; im Log sind 388 Mods aufgeführt. Die früheren PolyPlus-HTTP-500-/Mojang-503-Meldungen waren vorübergehend; die PolyPlus-Anmeldung gelang später. Sie erklären diesen konkreten Stacktrace nicht.

## Eingrenzung ohne Datenverlust

1. Eine Kopie von `options.txt` und `config/` anlegen, bevor Tastenzuordnungen oder Mods verändert werden.
2. Mit demselben Profil denselben Server jeweils nach einem vollständigen Neustart testen: zuerst ohne FWM, dann mit FWM. Bei einem Unterschied FWM-Tasten in den Minecraft-Steuerungseinstellungen testweise auf normale Tastaturtasten setzen; FWM kann gespeicherte Bindungen aus seiner eigenen Konfiguration wiederherstellen.
3. Den Vergleich zusätzlich in einer separaten schlanken Fabric-26.3-Installation nur mit Fabric API und FWM durchführen. Das grenzt Interaktionen mit OneClient und seinen Mods ein, ohne die bestehende Installation zu verändern.
4. Falls es nur in OneClient auftritt, Ixeris/OneClient aktualisieren und testweise deaktivieren, sofern der Client das zulässt. Der Stack nennt Ixeris ausdrücklich, beweist aber nicht, dass es die Ursache ist. Vor Änderungen im Launcher dessen Modpack-Verwaltung beachten.
5. Für jeden Lauf die letzten Logzeilen ab „Connecting to …“ und, falls vorhanden, den Disconnect-Bericht aus `debug/` sichern. Zugangstoken, persönliche Daten und Serverinformationen vor Weitergabe entfernen.

Es wurde noch kein Fix implementiert oder im Spiel getestet. Insbesondere sollten Exceptions bei Paketverarbeitung nicht pauschal unterdrückt werden: Dies kann einen ungültigen Clientzustand verbergen.
