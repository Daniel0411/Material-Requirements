package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Modelliert eine komplett aufgebaute Produktstruktur. Dadurch koennen die
 * Baugruppen, die Teil einer anderen Baugruppe sind oder deren Einzelteile
 * ermittelt werden. Ausserdem kann so eine Baugruppe auf Zyklenfreiheit
 * geprueft werden. Zyklenfreiheit sei hier so definiert wie in der
 * Aufgabenstellung.
 * 
 * @author Daniel Vollmer
 * @version 2.0
 */
public class ProductStructure {
    private TreeMap<String, Part> allParts = new TreeMap<String, Part>();

    /**
     * Setzt this.allParts auf eine tiefe Kopie des Paramters allParts. Dies ist
     * noetig, da eventuell Elemente hinzugefuegt werden, die einen Zyklus
     * verursachen und dann nicht in der original Map sein sollen.
     * 
     * @param allParts Die zu kopierende Map
     */
    public ProductStructure(Map<String, Part> allParts) {
        for (Map.Entry<String, Part> entry : allParts.entrySet()) {
            String name = new String(entry.getKey());
            Part part = new Part(entry.getValue());
            this.allParts.put(name, part);
        }
    }

    /**
     * Gibt eine Map mit dem Namen und der Anzahl aller indirekten und direkten
     * Einzelteile zurueck, aus denen eine gegebene Baugruppe besteht.
     * 
     * @param name                 Name der Baugruppe, deren Einzelteile gefunden
     *                             werden sollen.
     * @param multiplier           Multiplikator fuer die tieferen Rekursionsebenen.
     *                             Sollte beim erstmaliegen Aufrufen der Funktion 1
     *                             sein.
     * @param finalComponentsParam Map aller Einzelteile und deren Anzahl, die
     *                             bisher gefunden wurden.
     * @return Map mit Anzahl und Namen aller direkten und indirekten Einzelteile
     *         der Baugruppe.
     */
    public Map<String, Integer> getComponents(String name, int multiplier, Map<String, Integer> finalComponentsParam) {
        Map<String, Integer> finalComponents = finalComponentsParam;
        Map<String, Integer> components = new TreeMap<String, Integer>();
        Map<String, Integer> subParts = allParts.get(name).getSubParts();

        for (Map.Entry<String, Integer> entry : subParts.entrySet()) {
            components.put(entry.getKey(), entry.getValue() * multiplier);
        }

        for (Map.Entry<String, Integer> entry : components.entrySet()) {
            if (allParts.get(entry.getKey()).isComponent()) {
                finalComponents = addToValues(entry.getKey(), entry.getValue(), finalComponents);
            } else {
                finalComponents = getComponents(entry.getKey(), entry.getValue(), finalComponents);
            }

        }
        return finalComponents;

    }

    /**
     * Gibt eine Map mit dem Namen und der Anzahl aller indirekten und direkten
     * Baugruppen zurueck, aus denen eine gegebene Baugruppe besteht.
     * 
     * @param name                 Name der Baugruppe, deren Baugruppen gefunden
     *                             werden sollen.
     * @param multiplier           Multiplikator fuer die tieferen Rekursionsebenen.
     *                             Sollte beim erstmaliegen Aufrufen der Funktion 1
     *                             sein.
     * @param finalAssembliesParam Map aller Baugruppen und deren Anzahl, die bisher
     *                             gefunden wurden.
     * @return Map mit Anzahl und Namen aller direkten und indirekten Baugruppen,
     *         aus denen die gegebene Baugruppe besteht.
     */
    public Map<String, Integer> getAssemblies(String name, int multiplier, Map<String, Integer> finalAssembliesParam) {
        Map<String, Integer> finalAssemblies = finalAssembliesParam;
        Map<String, Integer> assemblies = new TreeMap<String, Integer>();
        Map<String, Integer> subParts = allParts.get(name).getSubParts();

        for (Map.Entry<String, Integer> entry : subParts.entrySet()) {
            if (!allParts.get(entry.getKey()).isComponent()) {
                assemblies.put(entry.getKey(), entry.getValue() * multiplier);
            }
        }

        for (Map.Entry<String, Integer> entry : assemblies.entrySet()) {
            if (areAllSubPartsComponents(entry.getKey())) {
                finalAssemblies = addToValues(entry.getKey(), entry.getValue(), finalAssemblies);
            } else {
                finalAssemblies = addToValues(entry.getKey(), entry.getValue(), finalAssemblies);
                finalAssemblies = getAssemblies(entry.getKey(), entry.getValue(), finalAssemblies);
            }
        }
        return finalAssemblies;

    }

    /**
     * Gibt true zurueck, wenn alle direkten Teile, aus denen eine Baugruppe besteht
     * Einzelteile sind, sonst false.
     * 
     * @param name Name der zu pruefenden Baugruppe.
     * @return true, wenn alle direkten Teile, aus denen die Baugruppe besteht
     *         Einzelteile sind, sonst false.
     */
    private boolean areAllSubPartsComponents(String name) {
        for (Map.Entry<String, Integer> entry : allParts.get(name).getSubParts().entrySet()) {
            if (!allParts.get(entry.getKey()).isComponent()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fuegt einer Map einen Eintrag hinzu. Falls der Key schon existiert, wird
     * amount auf die Zahl, die zu dem passenden Key in der Map gehoert aufaddiert
     * und wieder in der Map gespeichert.
     * 
     * @param name   Schluessel des neuen Eintrags.
     * @param amount Wert des neuen Eintrags.
     * @param toAdd  Map, bei der ein Element veraendert werden soll.
     * @return die leicht veraenderte Map toAdd
     */
    private Map<String, Integer> addToValues(String name, int amount, Map<String, Integer> toAdd) {
        Map<String, Integer> result = toAdd;

        if (toAdd.containsKey(name)) {
            result.put(name, amount + toAdd.get(name));
        } else {
            result.put(name, amount);
        }
        return result;
    }

    /**
     * Erhoeht die Anzahl eines Teils einer Baugruppe in der Testumgebung. Das
     * heiﬂt, dass das Teil nicht in der eigentlichen Map aller Teile veraendert
     * wird.
     * 
     * @param assemblyName Name der Baugruppe, bei dem die Anzahl eines Teils
     *                     veraendert werden soll.
     * @param amount       Zahl, um die die Anzahl veraendert werden soll.
     * @param subPartName  Teil, bei welchem die Anzahl veraendert werden soll.
     */
    private void incAmtOfPartInTestEnvironment(String assemblyName, int amount, String subPartName) {
        Part assembly = allParts.get(assemblyName);
        assembly.incAmount(subPartName, amount);

    }

    /**
     * Fuegt ein Teil der Testumgebung hinzu. Das heiﬂt, dass das Teil nicht der
     * eigentlichen Map aller Teile zugefuegt wird.
     * 
     * @param name    Name des hinzuzufuegenden Teils.
     * @param newPart Das hinzuzufuegende Teil.
     */
    private void addPartToTestEnvironment(String name, Part newPart) {
        for (Map.Entry<String, Integer> entry : newPart.getSubParts().entrySet()) {
            if (!allParts.containsKey(entry.getKey())) {
                allParts.put(entry.getKey(), new Part());
            }
        }
        allParts.put(name, newPart);
    }

    /**
     * Prueft, ob die Map aller Teile immer noch zyklusfrei ist, wenn ein neues Teil
     * hinzugefuegt wird. Gibt wahr zurueck, wenn die Map dann immer noch zyklusfrei
     * ist, sonst false.
     * 
     * @param name    Name des neuen Teils.
     * @param newPart Neues Teil.
     * @return true, wenn die Map nach hinzufuegen des neuen Teils noch zyklusfrei
     *         ist, sonst false.
     */
    public boolean areAllCycleFree(String name, Part newPart) {
        addPartToTestEnvironment(name, newPart);
        return areAllCycleFree(name);
    }

    /**
     * Prueft ob die Map aller Teile immer noch zyklusfrei ist, wenn die Anzahl
     * eines Teils, aus dem eine Baugruppe besteht veraendert wird. Gibt true
     * zurueck, wenn die Map danach immer noch zyklusfrei ist, sonst false.
     * 
     * @param assemblyName Baugruppe, bei der ein Sub-Teil veraendert wird.
     * @param amount       Zahl, um die die Anzahl erhoeht wird.
     * @param subPartName  Name des Teils, bei dem die Anzahl veraendert wird.
     * @return true, wenn die Map nach dem veraendern der Anzahl immer noch
     *         zyklusfrei ist, sonst false.
     */
    public boolean areAllCycleFree(String assemblyName, int amount, String subPartName) {
        incAmtOfPartInTestEnvironment(assemblyName, amount, subPartName);
        return areAllCycleFree(assemblyName);

    }

    /**
     * Ueberprueft alle Teile der Map allParts auf Zyklenfreiheit, beginnend mit dem
     * Teil dessen Name dem Wert von first entspricht. Sind alle Teile zyklenfrei,
     * wird true zurueckgegeben, sonst false.
     * 
     * @param first Name des Teils, mit dem begonnen werden soll.
     * @return true, wenn alle Teile in der Map allParts zyklenfrei sind, sonst
     *         false.
     */
    private boolean areAllCycleFree(String first) {
        // Fuer den Fall, dass z.b. Y=1:Y;1:Z
        if (!isCycleFree(first)) {
            return false;
        }

        for (Map.Entry<String, Part> entry : allParts.entrySet()) {
            if (!isCycleFree(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prueft, ob ein Teil in der Map allParts zyklenfrei ist. Gibt true zurueck,
     * wenn das Teil zyklenfrei ist, sonst false.
     * 
     * @param toCheck Teil, welches ueberprueft werden soll.
     * @return true, wenn das Teil zyklenfrei ist, sonst false.
     */
    private boolean isCycleFree(String toCheck) {
        List<String> list = new ArrayList<String>();
        list.add(toCheck);

        boolean containsOnlyComponents = false;

        if (allParts.get(toCheck).isComponent()) {
            return true;
        }

        while (!containsOnlyComponents) {
            containsOnlyComponents = true;
            int listSize = list.size();
            for (int i = 0; i < listSize; i++) {

                List<String> subParts = new ArrayList<String>();

                for (Map.Entry<String, Integer> entry : allParts.get(list.get(i)).getSubParts().entrySet()) {
                    subParts.add(entry.getKey());
                }

                String currentPart = list.get(i);

                if (subParts.size() != 0) {
                    list.remove(currentPart);
                    list.addAll(subParts);
                }

                if (list.contains(toCheck)) {
                    return false;
                }
            }
            // Ueberpruefung fortsetzen, so lange noch nicht alle Teile in der List
            // Einzelteile sind.
            for (String entry : list) {
                if (!allParts.get(entry).isComponent()) {
                    containsOnlyComponents = false;
                    break;
                }
            }
        }
        return true;
    }
}
