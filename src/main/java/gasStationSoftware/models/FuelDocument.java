package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class FuelDocument extends Document {

    private final ArrayList<DeliveredFuel> FUELS;

    /**
     * Constructor FuelDocument
     * @param docType
     * @param name
     * @param date
     * @param fuels
     * @author Robin Herder
     */
    public FuelDocument(DocumentType docType, String name, Date date, ArrayList<DeliveredFuel> fuels) {
        super(docType, name, date);
        FUELS = fuels;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    @Override
    public String[] getLinesForFile() {
        return new String[0];
    }

    /**
     * Gibt die Kraftstoffe zurück
     * @return
     * @author Robin Herder
     */
    public ArrayList<?> getFuels() {
        return FUELS;
    }

    /**
     * Gibt die Gesamtsumme zurück
     * @return
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
     *
     * @return
     * @author Robin Herder
     */
    @Override public String getTotalForTab() {
        return "- " + getTotal();
    }
}
