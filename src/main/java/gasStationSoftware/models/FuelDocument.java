package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class FuelDocument extends Document {

    private final ArrayList<DeliveredFuel> FUELS;

    public FuelDocument(DocumentType docType, String name, Date date, ArrayList<DeliveredFuel> fuels) {
        super(docType, name, date);
        FUELS = fuels;
    }

    @Override
    public String[] getLinesForFile() {
        return new String[0];
    }

    public ArrayList<?> getFuels() {
        return FUELS;
    }

}
