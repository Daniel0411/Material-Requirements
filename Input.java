package edu.kit.informatik;

/**
 * Eine eingegebene Zeile wird hier eingelesen, ohne sie jedoch auf Korrektheit
 * zu pruefen oder sie weiterzuverarbeiten. Die Zeile wird lediglich am ersten
 * Leerzeichen aufgeteilt, falls dieses vorhanden ist.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 *
 */
public class Input {
    private String inputString = "";
    private String command = "";
    private String rest = "";

    /**
     * Liest eine Zeile ein und splitted sie in den Befehl und den Rest auf.
     */
    public Input() {
        inputString = Terminal.readLine();
        splitString();
    }

    /**
     * Splitted einen String an den Leerzeichen. Setzt command auf den Teil vor dem
     * ersten Leerzeichen und rest auf den Rest, aber ohne Leerzeichen.
     */
    private void splitString() {
        if (containsBlank() && !startsWithBlank() && inputString.length() > 0) {
            command = inputString.split(" ")[0];
            for (int i = 1; i < inputString.split(" ").length; i++) {
                rest += inputString.split(" ")[i];
            }
        } else if (inputString.length() > 0 && !startsWithBlank()) {
            command = inputString;
        }
    }

    /**
     * Gibt true zurueck, falls der Input String mit einem Leerzeichen startet,
     * sonst false.
     * 
     * @return true falls erstes Zeichen Leerzeichen, sonst false.
     */
    public boolean startsWithBlank() {
        if (inputString.startsWith(" ")) {
            return true;
        }
        return false;
    }

    /**
     * Gibt true zurueck, falls letztes Zeichen des Input String ein Leerzeichen
     * ist, sonst false.
     * 
     * @return true, falls letzten Zeichen Leerzeichen, sonst false
     */
    public boolean lastCharBlank() {
        if (inputString.length() > 0) {
            if (inputString.substring(inputString.length() - 1).equals(" ")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt true zurueck, falls Input String ein Leerzeichen enthaelt, sonst false.
     * 
     * @return true, falls Leerzeichen enthalten, sonst false
     */
    public boolean containsBlank() {
        if (inputString.indexOf(" ") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * Gibt true zurueck, falls Input String mehr als ein Leerzeichen enthaelt,
     * sonst false.
     * 
     * @return true, falls mehr als ein Leerzeichen enthalten, sonst false
     */
    public boolean containsMoreThanOneBlank() {
        int count = 0;
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == ' ') {
                count++;
            }
        }
        if (count >= 2) {
            return true;
        }
        return false;
    }

    /**
     * Gibt command zurueck.
     * 
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gibt rest zurueck.
     * 
     * @return rest
     */
    public String getRest() {
        return rest;
    }
}
