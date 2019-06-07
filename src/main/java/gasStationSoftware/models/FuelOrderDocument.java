package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class FuelOrderDocument
extends Document {

    private HashMap<Item, Float> fuels = new HashMap<>();

    /**
     * Constructor GoodOrderDocument
     * @param docType     art des dokuments
     * @param name        name des dokuments
     * @param date        erstellungsdatum
     * @param fuels       Kraftstoff im doc
     * @author Robin Herder
     */
    public FuelOrderDocument(DocumentType docType, String name, Date date, ArrayList<Item> fuels) {
        super(docType, name, date);
        for(Item fuel : fuels) {
            this.fuels.put(fuel, fuel.getCheckoutAmount());
            fuel.setCheckoutAmount(0);
        }
    }

    /**
     * Gibt inhalt zum schrieben der datei zurück man beachte unterschied zu GoodOrderDocument wie in vorlage
     * @return zeilen für datei
     * @author Robin Herder
     */
    @Override public ArrayList<String> getLinesForFile() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Bestelldatum=" + getDATE());
        Iterator<Item> fuelKey = fuels.keySet().iterator();
        while (fuelKey.hasNext()) {
            Item fuel = fuelKey.next();
            lines.add(fuel.getINVENTORY_NUMBER() + ";" + fuels.get(fuel));
        }
        return lines;
    }

    /**
     * gibt Total zurück hier null
     * @return null
     * @author Robin Herder
     */
    @Override public String getTotalForTab() {
        return null;
    }

}
