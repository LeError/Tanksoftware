package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class FuelDocument extends Document {

    private final ArrayList<Fuel> FUELS;

    public FuelDocument(DocumentType docType, String name, Date date, ArrayList<Fuel> fuels) {
        super(docType, name, date);
        FUELS = fuels;
    }

    @Override
    public String[] getLinesForFile() {
        return new String[0];
    }

    public String[] getInvNumbers() {
        String[] invNumber = new String[FUELS.size()];
        for(int i = 0; i < FUELS.size(); i++) {
            invNumber[i] = String.valueOf(FUELS.get(i).getINVENTORY_NUMBER());
        }
        return invNumber;
    }

    public String[] getFuel() {
        String[] invNumber = new String[FUELS.size()];
        for(int i = 0; i < FUELS.size(); i++) {
            invNumber[i] = String.valueOf(FUELS.get(i).getINVENTORY_NUMBER());
        }
        return invNumber;
    }

    public String[] getAmount() {
        String[] invNumber = new String[FUELS.size()];
        for(int i = 0; i < FUELS.size(); i++) {
            invNumber[i] = String.valueOf(FUELS.get(i).getINVENTORY_NUMBER());
        }
        return invNumber;
    }

    public String[] getPrice() {
        String[] invNumber = new String[FUELS.size()];
        for(int i = 0; i < FUELS.size(); i++) {
            invNumber[i] = String.valueOf(FUELS.get(i).getINVENTORY_NUMBER());
        }
        return invNumber;
    }

    public String[] getCurrency() {
        String[] invNumber = new String[FUELS.size()];
        for(int i = 0; i < FUELS.size(); i++) {
            invNumber[i] = String.valueOf(FUELS.get(i).getINVENTORY_NUMBER());
        }
        return invNumber;
    }

}
