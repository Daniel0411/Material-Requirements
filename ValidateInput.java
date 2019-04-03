
package edu.kit.informatik;

import java.util.Map;
import java.util.TreeMap;

/**
 * Ueberprueft ob das Foramt der Eingabe stimmt, also ob der Befehl existiert
 * und korrekt ist und die dazugehoerenden Parameter ebenfalls das zum Befehl
 * passende Format haben und korrekt sind.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 *
 */
public class ValidateInput {
    private Command command;
    private PartName firstPart;
    private Map<String, Integer> parts = new TreeMap<String, Integer>();

    /**
     * Prueft ob eine Eingabe (Befehl und Parameter) korrekt ist. Wirft eine
     * Exception wenn die Eingabe inkorrekt ist. Falls sie korrekt ist wird command
     * auf den aktuellen Befehl gesetzt, firstPart wird auf das erste Teil das in
     * der Eingabe vorkommt gesetzt, in parts werden die uebrigen Teile und deren
     * Anzahl gespeichert.
     * 
     * @throws IllegalArgumentException falls Eingabe falsches Format hat oder
     *                                  inkorrekt ist.
     */
    public void validateCommand() throws IllegalArgumentException {
        Input input = new Input();
        String rest = input.getRest();

        command = Command.stringToEnum(input.getCommand());

        // Allgemeine Fehler die auftreten koennen.
        moreThanOneBlankException(input);
        noParameterException(rest);
        lastCharacterException(rest);

        // Befehls-Charakteristische Fehler pruefen.
        switch (command) {
        case QUIT:
            checkNoParameter(input);
            break;
        case ADD_ASSEMBLY:
            checkNewAssembly(rest);
            break;
        case REMOVE_ASSEMBLY:
            checkOneName(rest);
            break;
        case PRINT_ASSEMBLY:
            checkOneName(rest);
            break;
        case GET_ASSEMBLIES:
            checkOneName(rest);
            break;
        case GET_COMPONENTS:
            checkOneName(rest);
            break;
        case ADD_PART:
            checkAddPart(rest);
            break;
        case REMOVE_PART:
            checkRemovePart(rest);
            break;
        default:
            command = Command.ILLEGAL_COMMAND;
        }
    }

    /**
     * Gibt den Befehl der Eingabe zurueck.
     * 
     * @return Befehl der Eingabe.
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gibt eine Map der Teile und die dazugehoerige Anzahl zurueck (ausser das
     * erste Teil das bei den Paraemeter der Eingabe vorkommt.
     * 
     * @return Map der Teile (ausser erstes) und dazugehoerige Anzahl.
     */
    public Map<String, Integer> getParts() {
        return parts;
    }

    /**
     * Gibt den Namen des Ersten Teils der Eingabe zurueck.
     * 
     * @return Name des ersten Teils der Eingabe.
     */
    public String getFirstPartName() {
        if (firstPart != null) {
            return firstPart.toString();
        }
        return null;
    }

    /**
     * Ueberprueft die Eingabe fuer den Befehl addPart. Wirft eine Exception, wenn
     * die Parameter der Eingabe nicht korrekt sind. Wenn sie korrekt sind, wird die
     * Anzahl und das Teil welches verändert werden soll zu parts hinzugefuegt.
     * firstPart wird auf das erste Teil, welches in der Eingabe vorkommt gesetzt.
     * 
     * @throws IllegalArgumentException falls die Parameter der Eingabe nicht zum
     *                                  addPart Befehl passen.
     */
    private void checkAddPart(String rest) throws IllegalArgumentException {
        String[] splittedRest = rest.split("\\+");

        if (splittedRest.length != 2) {
            throw new IllegalArgumentException("the input contains either no or more than one plus signs!");
        }
        firstPart = new PartName(splittedRest[0]);
        addToParts(splittedRest[1]);
    }

    /**
     * Ueberprueft die Eingabe fuer den Befehl removePart. Wirft eine Exception,
     * wenn die Parameter der Eingabe nicht korrekt sind. Wenn sie korrekt sind,
     * wird die Anzahl und das Teil, welches verändert werden soll, zu parts
     * hinzugefuegt. firstPart wird auf das erste Teil, welches in der Eingabe
     * vorkommt gesetzt.
     * 
     * @throws IllegalArgumentException falls die Parameter der Eingabe nicht zum
     *                                  removePart Befehl passen.
     */
    private void checkRemovePart(String rest) throws IllegalArgumentException {
        String[] splittedRest = rest.split("-");

        if (splittedRest.length != 2) {
            throw new IllegalArgumentException("the input contains either no or more than one minus signs!");
        }
        firstPart = new PartName(splittedRest[0]);
        addToParts(splittedRest[1]);
    }

    /**
     * Ueberprueft die Eingabe fuer den Befehl addAssembly. Wirft eine Exception,
     * wenn die Parameter der Eingabe nicht korrekt sind. Wenn sie korrekt sind,
     * werden die Teile und die Anzahl, welche zur Baugruppe hinzugefuegt werden
     * sollen, zu parts hinzugefuegt. firstPart wird auf das erste Teil, welches in
     * der Eingabe vorkommt gesetzt.
     * 
     * @throws IllegalArgumentException falls die Parameter der Eingabe nicht zum
     *                                  addAssembly Befehl passen.
     */
    private void checkNewAssembly(String rest) throws IllegalArgumentException {
        String[] splittedRest = rest.split("=");

        if (splittedRest.length != 2) {
            throw new IllegalArgumentException("the input contains either no or more than one equal signs!");
        }

        firstPart = new PartName(splittedRest[0]);
        addToParts(splittedRest[1].split(";"));
    }

    /**
     * Fuegt einen String einem String Array hinzu, um das Teil und die Anzahl parts
     * hinzuzufuegen.
     * 
     * @param part Teil welches hinzugefuegt werden soll.
     * @throws IllegalArgumentException falls ein Teilename doppelt vorkommt, eine
     *                                  Anzahl keine Zahl ist oder falls eine Anzahl
     *                                  aber kein Name gegeben ist.
     */
    private void addToParts(String part) throws IllegalArgumentException {
        String[] stringPart = new String[1];
        stringPart[0] = part;
        addToParts(stringPart);
    }

    /**
     * Extrahiert aus einem String Array die Teile und deren Anzahl und fuegt sie
     * parts hinzu.
     * 
     * @param splittedParts Array, aus welchem die Teile und Anzahl extrahiert
     *                      werden soll.
     * @throws IllegalArgumentException falls ein Teilename doppelt vorkommt, eine
     *                                  Anzahl keine Zahl ist oder falls eine Anzahl
     *                                  aber kein Name gegeben ist.
     */
    private void addToParts(String[] splittedParts) throws IllegalArgumentException {
        for (int i = 0; i < splittedParts.length; i++) {
            try {
                PartName partName = new PartName(splittedParts[i].split(":")[1]);
                int amount = Integer.valueOf(splittedParts[i].split(":")[0]);
                if (parts.containsKey(partName.toString())) {
                    throw new IllegalArgumentException("at least one name of the parts appear twice!");
                }
                parts.put(partName.toString(), amount);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("the amount of at least one part is not a number!");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("the format of at least one part and it's amount is incorrect.");
            }
        }
    }

    /**
     * Prueft die Eingabe fuer den Fall, dass genau ein Name als Parameter erwartet
     * wird. Wirft eine Exception, falls dies nicht der Fall ist.
     * 
     * @throws IllegalArgumentException falls nicht ein Name als Parameter vorliegt.
     */
    private void checkOneName(String rest) throws IllegalArgumentException {
        if (rest.length() == 0) {
            throw new IllegalArgumentException("expected a name as parameter, no parameter is given!");
        }
        firstPart = new PartName(rest);
    }

    /**
     * Prueft die Eingabe fuer den Fall, dass keine Parameter erwartet werden. Wirft
     * eine Exception, falls doch welche vorliegen.
     * 
     * @throws IllegalArgumentException falls die Eingabe Parameter hat.
     */
    private void checkNoParameter(Input input) throws IllegalArgumentException {
        if (input.containsBlank()) {
            throw new IllegalArgumentException("no parameter allowed for the quit-command!");
        }
    }

    /**
     * Ueberprueft ob die Eingabe Parameter hat (ausser bei dem Befehl quit). Wirft
     * eine Exception, falls nicht.
     * 
     * @throws IllegalArgumentException falls der Befehl nicht quit ist und keine
     *                                  Parameter vorliegen.
     */
    private void noParameterException(String rest) throws IllegalArgumentException {
        if (command == Command.QUIT) {
            return;
        } else if (rest.length() == 0) {
            throw new IllegalArgumentException("parameters were expected but none are given!");
        }
    }

    /**
     * Wirft eine Exception, wenn das letzte Zeichen der Eingabe ";", ":", "=", "+"
     * oder "-" ist.
     * 
     * @throws IllegalArgumentException falls das letze zeichen der Eingabe ";",
     *                                  ":", "=", "+" oder "-" ist.
     */
    private void lastCharacterException(String rest) throws IllegalArgumentException {
        if (rest.length() == 0 || command == Command.QUIT) {
            return;
        }
        char lastChar = rest.charAt(rest.length() - 1);
        if (lastChar == ';' || lastChar == ':' || lastChar == '=' || lastChar == '+' || lastChar == '-') {
            throw new IllegalArgumentException("the last character of the input can't be " + lastChar);
        }
    }

    /**
     * Wirft Excpetion, falls der input mehr als ein Leerzeichen enthaelt.
     * 
     * @throws IllegalArgumentException falls input mehr als ein Leerzeichen
     *                                  enthaelt.
     */
    private void moreThanOneBlankException(Input input) throws IllegalArgumentException {
        if (input.containsMoreThanOneBlank()) {
            throw new IllegalArgumentException("too many blanks, wrong format!");
        }
    }
}