package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class FuelOrderDocument
extends Document {

    private final ArrayList<Fuel> FUELS;
    private final ArrayList<Float> FUELS_AMOUNT;

    /**
     * Constructor GoodOrderDocument
     *
     * @param docType     art des dokuments
     * @param name        name des dokuments
     * @param date        erstellungsdatum
     * @param fuels       Kraftstoff im doc
     * @param fuelsAmount Kraftstoff im doc
     * @author Robin Herder
     */
    public FuelOrderDocument(DocumentType docType, String name, Date date, ArrayList<Fuel> fuels,
    ArrayList<Float> fuelsAmount) {
        super(docType, name, date);
        FUELS = fuels;
        FUELS_AMOUNT = fuelsAmount;
    }

    /**
     * Gibt inhalt zum schrieben der datei zurück
     *
     * @return zeilen für datei
     * @author Robin Herder
     */
    @Override public ArrayList<String> getLinesForFile() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Bestelldatum=" + getDATE());
        lines.add("Warennummer;Bestellmenge");
        for (int i = 0; i < FUELS.size(); i++) {
            lines.add(FUELS.get(i).getINVENTORY_NUMBER() + ";" + FUELS_AMOUNT.get(i));
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
