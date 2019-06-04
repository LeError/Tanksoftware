package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class GoodDocument
extends Document {

    private final ArrayList<Good> GOODS;

    /**
     * Constructor GoodDocument
     * @param docType
     * @param name
     * @param date
     * @param goods
     * @author Robin Herder
     */
    public GoodDocument(DocumentType docType, String name, Date date, ArrayList<Good> goods) {
        super(docType, name, date);
        GOODS = goods;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    @Override public String[] getLinesForFile() {
        return new String[0];
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    public ArrayList<Good> getGoods() {
        return GOODS;
    }

    /**
     * Gibt den Gesamtpreis des Waren zurück
     * @return
     * @author Robin Herder
     */
    public float getTotal() {
        float total = 0;
        for (Good good : GOODS) {
            total += good.getAmount() * good.getPrice();
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
