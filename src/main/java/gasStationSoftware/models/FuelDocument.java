package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class FuelDocument extends Document {

    private final ArrayList<DeliveredFuel> FUELS;

    /**
     * Constructor FuelDocument
     * @param docType  DocumentType
     * @param name name des doc
     * @param date erstelungsdatum
     * @param fuels kraftstoffe die gelivert wurden
     * @author Robin Herder
     */
    public FuelDocument(DocumentType docType, String name, Date date, ArrayList<DeliveredFuel> fuels) {
        super(docType, name, date);
        FUELS = fuels;
    }

    /**
     * Zeilen für datei
     * @return lines
     * @author Robin Herder
     */
    @Override
    public String[] getLinesForFile() {
        return new String[0];
    }

    /**
     * Gibt die Kraftstoffe zurück
     * @return fuels
     * @author Robin Herder
     */
    public ArrayList<?> getFuels() {
        return FUELS;
    }

    /**
     * Gibt die Gesamtsumme zurück
     * @return total
     * @author Robin Herder
     */
    public float getTotal() {
        float total = 0;
        for (DeliveredFuel fuel : FUELS) {
            total += fuel.getAmountDelivered() * fuel.getPrice();
        }
        return total;
    }

    /**
     * Summe formatiert für Tab
     * @return total
     * @author Robin Herder
     */
    @Override public String getTotalForTab() {
        return "- " + getTotal();
    }
}
