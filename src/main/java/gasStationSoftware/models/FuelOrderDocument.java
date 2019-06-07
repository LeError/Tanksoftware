package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class FuelOrderDocument
extends Document {

    private final ArrayList<Item> FUELS;

    /**
     * Constructor GoodOrderDocument
     *
     * @param docType     art des dokuments
     * @param name        name des dokuments
     * @param date        erstellungsdatum
     * @param fuels       Kraftstoff im doc
     * @author Robin Herder
     */
    public FuelOrderDocument(DocumentType docType, String name, Date date, ArrayList<Item> fuels) {
        super(docType, name, date);
        FUELS = fuels;
    }

    /**
     * Gibt inhalt zum schrieben der datei zurück man beachte unterschied zu GoodOrderDocument wie in vorlage
     * @return zeilen für datei
     * @author Robin Herder
     */
    @Override public ArrayList<String> getLinesForFile() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Bestelldatum=" + getDATE());
        for (int i = 0; i < FUELS.size(); i++) {
            lines.add(FUELS.get(i).getINVENTORY_NUMBER() + ";" + FUELS.get(i).getCheckoutAmount());
        }
        return lines;
    }

    /**
     * gibt Total zurück hier null
     *
     * @return null
     * @author Robin Herder
     */
    @Override public String getTotalForTab() {
        return null;
    }

}
