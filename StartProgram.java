package edu.kit.informatik;

/**
 * Startet das Materialverwaltungsprogramm und laesst es so lange laufen, bis
 * der quit Befehl erfolgreich ausgefuehrt wird.
 * 
 * @author Daniel Vollmer
 * @version 1.0
 */
public class StartProgram {

    /**
     * Startet das Materialverwaltungsprogramm und laesst es so lange laufen, bis
     * der quit Befehl erfolgreich ausgefuehrt wird.
     */
    public void start() {
        PartList allParts = new PartList();

        while (Command.isStillRunning()) {
            ValidateInput input = new ValidateInput();
            try {
                // Format der Eingabe auf Korrektheit ueberpruefen.
                input.validateCommand();
                // Versuchen einen eingegebenen Befehl auszufuehren.
                input.getCommand().execute(allParts, input.getParts(), input.getFirstPartName());
            } catch (IllegalArgumentException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }
}
