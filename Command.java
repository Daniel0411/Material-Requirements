package edu.kit.informatik;

import java.util.Map;

/**
 * Enum beinhaltet alle Befehle, deren Ausführung und wie die Enums als String
 * geschrieben werden.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 * 
 */
public enum Command {
    /**
     * Modelliert den Befehl "addAssembly", also dessen Ausfuehrung und Darstellung
     * als String.
     */
    ADD_ASSEMBLY("addAssembly") {
        /**
         * Fuehrt den Befehl "addAssembly" aus. Falls der Befehl erfolgreich ausgefuehrt
         * wurde, wird "OK" ausgegeben.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das Teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly)
                throws IllegalArgumentException {
            allParts.addAssembly(firstAssembly, newParts);
            Output.printOK();
        }
    },
    /**
     * Modelliert den Befehl "removeAssembly", also dessen Ausfuehrung und
     * Darstellung als String.
     */
    REMOVE_ASSEMBLY("removeAssembly") {
        /**
         * Fuehrt den Befehl "removeAssembly" aus. Falls der Befehl erfolgreich
         * ausgefuehrt wurde, wird "OK" ausgegeben.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das Teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            allParts.removeAssembly(firstAssembly);
            Output.printOK();
        }
    },
    /**
     * Modelliert den Befehl "printAssembly", also dessen Ausfuehrung und
     * Darstellung als String.
     */
    PRINT_ASSEMBLY("printAssembly") {
        /**
         * Fuehrt den Befehl "printAssembly" aus.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            Output.printPart(allParts.partToString(firstAssembly));
        }
    },
    /**
     * Modelliert den Befehl "getAssemblies", also dessen Ausfuehrung und
     * Darstellung als String.
     */
    GET_ASSEMBLIES("getAssemblies") {
        /**
         * Fuehrt den Befehl "getAssemblies" aus.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            Output.printAssembliesOrComponents(allParts.getAssemblies(firstAssembly));
        }
    },
    /**
     * Modelliert den Befehl "getComponents", also dessen Ausfuehrung und
     * Darstellung als String.
     */
    GET_COMPONENTS("getComponents") {
        /**
         * Fuehrt den Befehl "getComponents" aus.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            Output.printAssembliesOrComponents(allParts.getComponents(firstAssembly));
        }
    },
    /**
     * Modelliert den Befehl "addPart", also dessen Ausfuehrung und Darstellung als
     * String.
     */
    ADD_PART("addPart") {
        /**
         * Fuehrt den Befehl "addPart" aus. Falls der Befehl erfolgreich ausgefuehrt
         * wurde, wird "OK" ausgegeben.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            for (Map.Entry<String, Integer> entry : newParts.entrySet()) {
                allParts.incAmount(firstAssembly, entry.getValue(), entry.getKey());
            }
            Output.printOK();
        }
    },
    /**
     * Modelliert den Befehl "removePart", also dessen Ausfuehrung und Darstellung
     * als String.
     */
    REMOVE_PART("removePart") {
        /**
         * Fuehrt den Befehl "removePart" aus. Falls der Befehl erfolgreich ausgefuehrt
         * wurde, wird "OK" ausgegeben.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            for (Map.Entry<String, Integer> entry : newParts.entrySet()) {
                allParts.decAmount(firstAssembly, entry.getValue(), entry.getKey());
            }
            Output.printOK();
        }
    },
    /**
     * Modelliert den Befehl "quit", also dessen Ausfuehrung und Darstellung als
     * String.
     */
    QUIT("quit") {
        /**
         * Fuehrt den Befehl "quit" aus.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            stillRunning = false;
        }
    },
    /**
     * Enum, das einen nicht korrekten Befehl modelliert.
     */
    ILLEGAL_COMMAND("illegal") {
        /**
         * Wirft eine Exception, dass der Befehl ungueltig ist.
         *
         * @param allParts      Alle Teile, die im System existieren.
         * @param newParts      Neue Teile, die verarbeitet werden sollen.
         * @param firstAssembly Das teil, welches das erste Teil der Parameter des
         *                      Inputs ist.
         * @throws IllegalArgumentException falls eine Eingabe inkorrekt war und der
         *                                  Befehl nicht ausgefuehrt werden konte.
         */
        public void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly) {
            throw new IllegalArgumentException("command doesn't exist!");
        }
    };

    /**
     * Variable, die anzeigt, ob das Programm weiter laufen soll, oder beendet
     * werden soll (durch quit).
     */
    private static boolean stillRunning = true;
    private String asString;
    
    /**
     * Setzt asString auf den Wert, der vom jeweiligen Enum uebergeben wird und
     * besagt, wie ein Enum als String dargestellt wird.
     * 
     * @param asString
     */
    private Command(String asString) {
        this.asString = asString;
    }

    /**
     * Gibt true zurueck, wenn das Programm noch laufen soll, also nicht quit
     * aufgerufen wurde, sonst false.
     * 
     * @return true, wenn das Programm weiterlaufen soll, sonst false.
     */
    public static boolean isStillRunning() {
        return stillRunning;
    }

    /**
     * Gibt den jeweiligen String zurueck.
     * 
     * @return Enum als String
     */
    public String getString() {
        return asString;
    }

    /**
     * Iteriert ueber alle Enums und vergleicht, ob die jeweiligen String
     * Darstellungen der Enums mit dem uebergebenen Wert uebereinstimmen. Falls ja
     * wird dieses Enum zurueckgegeben, falls keins uebereinstimmt wird
     * ILLEGAL_COMMAND zurueckgegeben.
     * 
     * @param command String zu dem das passende Enum gesucht wird.
     * @return null, falls kein Enum gefunden wurde, sonst das passende Enum.
     */
    public static Command stringToEnum(String command) {
        Command toReturn = ILLEGAL_COMMAND;
        for (Command type : Command.values()) {
            if (type.getString().equals(command)) {
                toReturn = type;
            }
        }
        return toReturn;
    }

    /**
     * Abstrakte Methode, die die Ausfuehrung der einzelnen Befehle darstellen soll.
     * Der Uebersichtlichkeit und Einheitlichkeit wegen ist es gewollt, dass alle
     * execute Funktionen die selbe Signatur haben, obwohl nicht fuer alle Befehle
     * alle Parameter benoetigt werden.
     * 
     * @param allParts      Alle Teile, die im System existieren.
     * @param newParts      Neue Teile, die verarbeitet werden sollen.
     * @param firstAssembly Das teil, welches das erste Teil der Parameter des
     *                      Inputs ist.
     * @throws IllegalArgumentException falls der Befehl nicht erfolgreich
     *                                  ausgefuehrt wurde.
     */
    public abstract void execute(PartList allParts, Map<String, Integer> newParts, String firstAssembly)
            throws IllegalArgumentException;
}
