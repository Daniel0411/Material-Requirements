package edu.kit.informatik;

import java.util.TreeMap;
import java.util.Map;

/**
 * Modelliert ein Teil. Dieses wird dabei nicht weiter zwischen Baugruppe und
 * Einzelteil unterschieden, da der einzige Unterschied ist, ob das Teil aus
 * anderen Teilen besteht oder nicht, und dies in einer Map gespeichert werden
 * kann. Ist diese Map leer, so is das Teil ein Einzelteil, hat die Map
 * mindestens einen Eintrag, so ist es eine Baugruppe. In der Map subParts
 * werden die Teile und deren Anzahl gespeichert, aus denen dieses Teil besteht.
 * Es ist also gewollt so, dass Assembly und Component nicht Unterklassen einer
 * Klasse Part sind, da diese Aufteilung nicht wirklich noetig ist und dies das
 * Ganze nicht vereinfachen wuerde.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 * 
 */
public class Part {
    private Map<String, Integer> subParts;

    /**
     * Konstruktor, der den direkten Sub-Teilen dieses Teils eine Map hinzufuegt,
     * falls jedes dieser Teile nicht öfter als 1000 mal vorkommt und mindestens
     * einmal (values der Map < 1000 und > 0). Falls doch wird eine Exception
     * geworfen.
     * 
     * @param subParts Teile die hinzugefuegt werden sollen.
     * @throws IllegalArgumentException falls ein Teil oefter als 1000 mal vorkommt.
     */
    public Part(Map<String, Integer> subParts) throws IllegalArgumentException {
        for (Map.Entry<String, Integer> entry : subParts.entrySet()) {
            if (entry.getValue() > 1000) {
                throw new IllegalArgumentException(
                        "it's not possible to add more than 1000 pieces of one part to an assembly!");
            } else if (entry.getValue() < 1) {
                throw new IllegalArgumentException("the amount of all parts must at least be 1!");
            }
        }
        this.subParts = new TreeMap<String, Integer>();
        this.subParts.putAll(subParts);
    }

    /**
     * Erstellt ein Teil, dessen Map von Unter-Teilen leer ist.
     */
    public Part() {
        subParts = new TreeMap<String, Integer>();
    }

    /**
     * Konstruktor fuer ein Teil, welches die Eigenschaften des im Parameter
     * angegeben Teils kopiert.
     * 
     * @param part Teil, welches kopiert werden soll.
     */
    public Part(Part part) {
        subParts = new TreeMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : part.getSubParts().entrySet()) {
            String name = new String(entry.getKey());
            Integer amount = new Integer(entry.getValue());
            subParts.put(name, amount);
        }
    }

    /**
     * Leert die Sub-Teile Map. Macht das Teil also zu einem Einzelteil.
     */
    public void toComponent() {
        subParts = new TreeMap<String, Integer>();
    }

    /**
     * Gibt die Map der Teile zurueck, aus denen dieses Teil besteht.
     * 
     * @return Map der Teile aus denen dieses Teil besteht.
     */
    public Map<String, Integer> getSubParts() {
        return subParts;
    }

    /**
     * Gibt true zurueck, falls dieses Teil aus keinen anderen Teilen besteht, also
     * ein Einzelteil ist, sont false.
     * 
     * @return true, wenn die subParts Map leer ist, sonst false.
     */
    public boolean isComponent() {
        if (subParts.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Erhoeht die Anzahl eines Teils in subParts. Falls das Teil noch nicht
     * existiert, wird es hinzugefuegt. Ueberschreitet die Anzahl des Teils in
     * subParts 1000 wird eine Exception geworfen oder wenn man versucht ein Teil
     * mit der Anzahl 0 hinzuzufuegen.
     * 
     * @param name   Name des Teils, dessen Anzahl erhoeht werden soll.
     * @param amount Zahl, um die die Anzahl des Teils erhoeht wird.
     * @throws IllegalArgumentException falls die Anzahl des Teils in subParts 1000
     *                                  ueberschreitet oder man ein Teil mit der
     *                                  Anzahl 0 hinzufuegen moechte.
     */
    public void incAmount(String name, int amount) throws IllegalArgumentException {
        if (amount < 1) {
            throw new IllegalArgumentException("the amount of the part you are trying to add must be at least 1!");
        } else if (amount > 1000) {
            throw new IllegalArgumentException(
                    "it's not possible to add more than 1000 pieces of one part to an assembly!");
        }
        if (subParts.containsKey(name)) {
            int oldAmount = subParts.get(name);
            if (oldAmount + amount > 1000) {
                throw new IllegalArgumentException(
                        "it's not possible to add more than 1000 pieces of one part to an assembly!");
            }
            subParts.put(name, oldAmount + amount);
        } else {
            subParts.put(name, amount);
        }
    }

    /**
     * Vermindert die Anzahl eines Teils in subParts. Wirft eine Exception, falls
     * das Teil nicht in subParts existiert, oder die Anzahl des Teils in subParts
     * geringer ist, als die Anzahl, um die es verringert werden soll. Entfernt das
     * Teil aus subParts, wenn dessen Anzahl 0 ist.
     * 
     * @param name   Name des Teils, von dem die Anzahl vermindert werden soll.
     * @param amount Zahl, um die die Anzahl des Teils vermindert werden soll.
     * @throws IllegalArgumentException falls, das Teil nicht in subParts existiert,
     *                                  oder die Anzahl des Teils in subParts
     *                                  geringer ist, als die Anzahl, um die es
     *                                  verringert werden soll
     */
    public void decAmount(String name, int amount) throws IllegalArgumentException {
        if (amount < 1) {
            throw new IllegalArgumentException("the amount of the part you are trying to remove must be at least 1!");
        } else if (!subParts.containsKey(name)) {
            throw new IllegalArgumentException("the given assembly does not contain the part " + name + "!");
        }
        int oldAmount = subParts.get(name);
        if (oldAmount == amount) {
            subParts.remove(name);
        } else if (oldAmount < amount) {
            throw new IllegalArgumentException(
                    "the amount of Part " + name + " in the given assembly is lower than the given amount!");
        } else {
            subParts.put(name, oldAmount - amount);
        }
    }

    @Override
    public String toString() {
        if (isComponent()) {
            return "COMPONENT";
        }

        String toReturn = "";
        for (Map.Entry<String, Integer> entry : subParts.entrySet()) {

            toReturn += entry.getKey() + ":" + entry.getValue() + ";";
        }
        if (toReturn.length() > 0 && toReturn.charAt(toReturn.length() - 1) == ';') {
            toReturn = toReturn.substring(0, toReturn.length() - 1);
        }
        return toReturn;
    }
}
