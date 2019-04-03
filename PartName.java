package edu.kit.informatik;

/**
 * Modelliert einen Namen einer Baugruppe oder eines Einzelteils. Dieser darf
 * nur aus den Zeichen a-z oder A-Z bestehen.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 */
public class PartName {
    /**
     * Name des Teils.
     */
    private String name;

    /**
     * Konstruktor, welcher name setzt, falls der gegebene Parameter im korrekten
     * Format ist (alle Zeichen zwischen a-z oder A-Z), wirft sonst eine Exception.
     * 
     * @param name Zu setzender Name.
     * @throws IllegalArgumentException falls der gegebene Parameter das falsche
     *                                  Format hat.
     */
    public PartName(String name) throws IllegalArgumentException {
        if (!isNameValid(name)) {
            throw new IllegalArgumentException(
                    "at least one character in the name is not between a-z or A-Z!");
        }
        this.name = name;
    }

    /**
     * Prueft, ob alle Zeichen des Parameters name zwischen a-z oder A-Z sind. Falls
     * ja wird true zurueckgegeben, sonst false.
     * 
     * @param name Zu pruefender Parameter
     * @return true, falls alle Zeichen zwischen a-z oder A-Z sind, sonst false.
     */
    private boolean isNameValid(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < 65 || (name.charAt(i) > 90 && name.charAt(i) < 97)
                    || name.charAt(i) > 122) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gibt den Namen zurueck.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt den Namen als String zurueck. Gleiche Funktion wie getName, der
     * Uebersichtlichkeit halber haben beide Funktionen jedoch unetrschiedliche
     * Namen.
     */
    @Override
    public String toString() {
        return name;
    }
}
