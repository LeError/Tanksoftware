package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class GoodDocument
extends Document {

    private final ArrayList<Good> GOODS;

    public GoodDocument(DocumentType docType, String name, Date date, ArrayList<Good> goods) {
        super(docType, name, date);
        GOODS = goods;
    }

    @Override public String[] getLinesForFile() {
        return new String[0];
    }

    public ArrayList<Good> getGoods() {
        return GOODS;
    }

    public float getTotal() {
        float total = 0;
        for (Good good : GOODS) {
            total += good.getAmount() * good.getPrice();
        }
        return total;
    }

    @Override public String getTotalForTab() {
        return "- " + getTotal();
    }

}
