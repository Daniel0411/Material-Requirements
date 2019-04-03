package edu.kit.informatik;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Modelliert und verwaltet den gesamten Output.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 *
 */
public class Output {

    /**
     * Gibt ein Teil aus.
     * 
     * @param part Teil, welches ausgegeben werden soll.
     */
    public static void printPart(String part) {
        Terminal.printLine(part);
    }

    /**
     * Gibt "OK" aus.
     */
    public static void printOK() {
        Terminal.printLine("OK");
    }

    /**
     * Gibt eine Map von Baugruppen oder Einzelkomponenten aus. Diese sind dabei
     * absteigend nach ihrer Anzahl sortiert, wenn die Anzahl gleich ist, werden
     * diese dann lexikografisch aufsteigend sortiert (nach dem Unicode Wert).
     * 
     * @param assembliesOrComponents Auszugebende Baugruppen oder Einzelteile.
     */
    public static void printAssembliesOrComponents(Map<String, Integer> assembliesOrComponents) {
        Map<Integer, Set<String>> toPrint = new TreeMap<Integer, Set<String>>();

        // In der Map toPrint werden alle Teile und die dazugehoerige Anzahl
        // gespeichert. Dabei ist die Anzahl der Schluessel, da nach ihr sortiert werden
        // soll und die Werte sind TreeSets aus Teilenamen, da diese dann ebenfalls
        // direkt sortiert sind, im Fall, dass zwei Teile die selbe Anzahl haben.
        for (Map.Entry<String, Integer> entry : assembliesOrComponents.entrySet()) {
            String name = entry.getKey();
            int amount = entry.getValue();

            if (!toPrint.containsKey(amount)) {
                Set<String> toAdd = new TreeSet<String>();
                toAdd.add(name);
                toPrint.put(amount, toAdd);
            } else {
                toPrint.get(amount).add(name);
            }
        }

        // Da die Anzah absteigend sein soll, die Reihenfolge der Map aendern.
        toPrint = ((TreeMap<Integer, Set<String>>) toPrint).descendingMap();

        if (toPrint.size() == 0) {
            Terminal.printLine("EMPTY");
            return;
        }

        String print = "";

        // Die Schluessel und Werte der Map toPrint, gemaess ihrer Sortierung, einem
        // String hinzufuegen, der dann ausgegeben wird.
        for (Map.Entry<Integer, Set<String>> entry : toPrint.entrySet()) {
            int amount = entry.getKey();
            Set<String> nameSet = entry.getValue();
            for (String name : nameSet) {
                print += name + ":" + amount + ";";
            }
        }

        if (print.length() > 0 && print.charAt(print.length() - 1) == ';') {
            print = print.substring(0, print.length() - 1);
        }
        Terminal.printLine(print);
    }
}
