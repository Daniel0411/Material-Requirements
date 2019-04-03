package edu.kit.informatik;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Modelliert die Liste aller Teile, die aktuell im System sind. Stellt
 * ausserdem Funktionen bereit, um Teile zu entfernen, hinzuzufuegen oder die
 * Anzahl einzelner Sub-Teile einer Baugruppe zu veraendern, ohne dass dabei die
 * Zyklenfreiheit oder aehnliches verletzt wird.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 */
public class PartList {
    private Map<String, Part> parts = new TreeMap<String, Part>();

    /**
     * Gibt eine Map aller Baugruppen und deren Anzahl zurueck, die indirekt oder
     * direkt teil einer anderen Baugruppe sind. Wirft eine Exception, wenn die
     * gegebene Baugruppe nicht existiert.
     * 
     * @param name Name der Baugruppe, von der man alle Sub-Baugruppen moechte.
     * @return Map aller Sub-Baugruppen und deren Anzahl.
     * @throws IllegalArgumentException falls die gegebene Baugruppe nicht
     *                                  existiert.
     */
    public Map<String, Integer> getAssemblies(String name) throws IllegalArgumentException {
        if (!parts.containsKey(name)) {
            throw new IllegalArgumentException("the given assembly doesn not exist!");
        } else if (parts.get(name).isComponent()) {
            throw new IllegalArgumentException("you can only get assemblies of another assembly not a component!");
        }
        ProductStructure pStructure = new ProductStructure(parts);
        return pStructure.getAssemblies(name, 1, new TreeMap<String, Integer>());
    }

    /**
     * Gibt eine Map aller Einzelteile und deren Anzahl zurueck, die indirekt oder
     * direkt Teil einer Baugruppe sind. Wirft eine Exception, wenn die gegebene
     * Baugruppe nicht existiert.
     * 
     * @param name Name der Baugruppe, von der man alle Sub-Einzelteile moechte.
     * @return Map aller Sub-Einzelteile und deren Anzahl
     * @throws IllegalArgumentException falls die gegebene Baugruppe nicht
     *                                  existiert.
     */
    public Map<String, Integer> getComponents(String name) throws IllegalArgumentException {
        if (!parts.containsKey(name)) {
            throw new IllegalArgumentException("the given assembly doesn not exist!");
        } else if (parts.get(name).isComponent()) {
            throw new IllegalArgumentException("you can only get components of an assembly not a component!");
        }
        ProductStructure pStructure = new ProductStructure(parts);
        return pStructure.getComponents(name, 1, new TreeMap<String, Integer>());
    }

    /**
     * Erhoeht die Anzahl, wie oft eine Baugruppe oder ein Einzelteil in einer
     * anderen Baugruppe vorkommt, ohne dabei zyklenfreiheit zu verletzen. Existiert
     * das Teil noch nicht, wird es zu der Baugruppe hinzugefuegt. Dadurch koennen
     * dem System auch neue Einzelteile hinzugefuegt werden.
     * 
     * @param assemblyName Baugruppe, bei der die Anzahl eines Teils veraendert
     *                     werden soll-
     * @param amount       Zahl, um die die Anzahl des Teils erhoeht werden soll.
     * @param subPartName  Teil, von welchem die Anzahl erhoeht werden soll.
     * @throws IllegalArgumentException falls die gegebene Baugruppe nicht existiert
     *                                  oder das Teil, von welchem man die Anzahl
     *                                  erhoehen will nicht existiert oder ein
     *                                  Zyklus entstehen wuerde.
     */
    public void incAmount(String assemblyName, int amount, String subPartName) throws IllegalArgumentException {
        Part assembly = parts.get(assemblyName);
        if (!parts.containsKey(assemblyName) || assembly.isComponent()) {
            throw new IllegalArgumentException("the given assembly doesn't exist!");
        }
        // Falls das Teil, von welchem man die Anzahl erhoehen will, noch nicht im
        // System existiert, hinzufuegen.
        else if (!parts.containsKey(subPartName)) {
            parts.put(subPartName, new Part());
        }

        ProductStructure pStructure = new ProductStructure(parts);
        if (!pStructure.areAllCycleFree(assemblyName, amount, subPartName)) {
            throw new IllegalArgumentException("the new parts would create a cycle!");
        }

        assembly.incAmount(subPartName, amount);
    }

    /**
     * Vermindert die Anzahl, wie oft eine Baugruppe oder ein Einzelteil in einer
     * anderen Baugruppe vorkommt. Entfernt die Teile gegebenenfalls aus der Map
     * aller Teile (parts) falls, diese nach der Verminderung nicht mehr existieren
     * sollen.
     * 
     * @param assemblyName Baugruppe, bei der die Anzahl eines Teils vermindert
     *                     werden soll.
     * @param amount       Zahl, um die die Anzahl des Teils vermindert werden soll.
     * @param subPartName  Teil, von welchem die Anzahl vermindert werden soll.
     * @throws IllegalArgumentException falls die gegebene Baugruppen oder
     *                                  Einzelteil nicht existieren
     */
    public void decAmount(String assemblyName, int amount, String subPartName) throws IllegalArgumentException {
        Part assembly = parts.get(assemblyName);
        if (!parts.containsKey(assemblyName) || assembly.isComponent()) {
            throw new IllegalArgumentException("the given assembly doesn't exist!");
        }
        assembly.decAmount(subPartName, amount);

        if (!parts.get(assemblyName).getSubParts().containsKey(subPartName)) {
            // Entfernen des Sub-Teils oder des Teils, von dem etwas abgezogen wird, falls
            // diese danach in keinem anderen Teil mehr vorkommen.
            if (!hasSuperPart(assemblyName, null)) {
                parts.remove(assemblyName);
            }
            if (parts.get(subPartName).isComponent() && !hasSuperPart(subPartName, null)) {
                parts.remove(subPartName);
            }
        }
    }

    /**
     * Fuegt der Map aller Teile (parts) eine Baugruppe hinzu. Dabei wird
     * sichergestellt, dass dadurch kein Zyklus entsteht.
     * 
     * @param assemblyName Name der Baugruppe die hinzugefuegt werden soll.
     * @param subParts     die Teile aus denen die Baugruppe besteht, die
     *                     hinzugefuegt werden soll.
     * @throws IllegalArgumentException falls die Baugruppe die hinzugefuegt werden
     *                                  soll schon existiert oder ein Zyklus
     *                                  entstehen wuerde.
     */
    public void addAssembly(String assemblyName, Map<String, Integer> subParts) throws IllegalArgumentException {
        if (parts.containsKey(assemblyName) && !parts.get(assemblyName).isComponent()) {
            throw new IllegalArgumentException("the given assembly already exists!");
        }

        ProductStructure pStructure = new ProductStructure(parts);
        if (!pStructure.areAllCycleFree(assemblyName, new Part(subParts))) {
            throw new IllegalArgumentException("the new assembly would create a cycle!");
        }

        // Jedes Sub-Teil, das noch nicht existiert, als Einzelteil zum System
        // hinzufuegen
        for (Map.Entry<String, Integer> entry : subParts.entrySet()) {
            if (!parts.containsKey(entry.getKey())) {
                addComponent(entry.getKey());
            }
        }

        parts.put(assemblyName, new Part(subParts));
    }

    /**
     * Fuegt der Map parts ein Einzelteil hinzu.
     * 
     * @param name Name des Einzelteils.
     */
    private void addComponent(String name) {
        parts.put(name, new Part());
    }

    /**
     * Entfernt eine Baugruppe aus der Map parts, sofern sie nicht Teil einer
     * anderen Baugruppe ist. Wenn diese Baugruppe noch Teil einer anderen Baugruppe
     * ist, wird sie nur zu einem Einzelteil und nicht komplett aus parts entfernt.
     * 
     * @param name Name der zu entfernenden Baugruppe
     * @throws IllegalArgumentException falls die Baugruppe nicht existiert oder ein
     *                                  Teil existiert, das den selben Namen hat,
     *                                  jedoch ein Einzelteil ist.
     */
    public void removeAssembly(String name) throws IllegalArgumentException {
        Part toRemove = parts.get(name);
        if (!parts.containsKey(name)) {
            throw new IllegalArgumentException("the given assembly doesn't exist!");
        } else if (toRemove.isComponent()) {
            throw new IllegalArgumentException("only assemblies can be reomved!");
        }

        Map<String, Integer> subParts = toRemove.getSubParts();
        Set<Map.Entry<String, Part>> set = parts.entrySet();
        Iterator<Map.Entry<String, Part>> iter = set.iterator();

        // Jedes Sub-Teil der zu entfernenden Baugruppe entfernen, wenn es in keiner
        // anderen Baugruppe mehr vorkommt und eine Einzelkomponente ist.
        while (iter.hasNext()) {
            Map.Entry<String, Part> entry = iter.next();
            if (subParts.containsKey(entry.getKey())) {
                if (!hasSuperPart(entry.getKey(), name) && entry.getValue().isComponent()) {
                    iter.remove();

                }
            }
        }

        // zu entfernende Baugruppe entfernen, falls sie nicht teil einer anderen
        // Baugruppe ist, sonst zu einer Einzelkomponente machen.
        if (!hasSuperPart(name, name)) {
            parts.remove(name);
            return;
        }
        toRemove.toComponent();
    }

    /**
     * Gibt ein Teil, welches in der Map parts ist als String zurueck.
     * 
     * @param name Name des Teils, welches als String zuruekgegeben werden soll.
     * @return Darstellung des Teiles als String.
     * @throws IllegalArgumentException falls das Teil nicht in der Map parts ist.
     */
    public String partToString(String name) throws IllegalArgumentException {
        if (!parts.containsKey(name)) {
            throw new IllegalArgumentException("part doesn't exist!");
        }
        return parts.get(name).toString();
    }

    /**
     * Gibt true zurueck, wenn ein Teil noch teil anderer Baugruppen ist, sonst
     * false.
     * 
     * @param name             Name des Teils, welches ueberprueft werden soll.
     * @param currentSuperPart Eine Baugruppe welche bei der Ueberpruefung
     *                         ausgeschlossen werden soll.
     * @return true, wenn das Teil noch in anderen Baugruppen existiert, sonst
     *         false.
     */
    public boolean hasSuperPart(String name, String currentSuperPart) {
        for (Map.Entry<String, Part> entry : parts.entrySet()) {
            if (entry.getValue().getSubParts().containsKey(name) && !entry.getKey().equals(currentSuperPart)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String toReturn = "";
        for (Map.Entry<String, Part> entry : parts.entrySet()) {
            toReturn += entry.getKey() + ":" + entry.getValue() + ";";
        }
        if (toReturn.length() > 0 && toReturn.charAt(toReturn.length() - 1) == ';') {
            toReturn = toReturn.substring(0, toReturn.length() - 1);
        }
        return toReturn;
    }
}