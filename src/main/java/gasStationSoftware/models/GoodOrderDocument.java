package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class GoodOrderDocument
extends Document {

    private HashMap<Item, Float> goods = new HashMap<>();

    /**
     * Constructor GoodOrderDocument
     * @param docType     art des dokuments
     * @param name        name des dokuments
     * @param date        erstellungsdatum
     * @param goods       wahren im doc
     * @author Robin Herder
     */
    public GoodOrderDocument(DocumentType docType, String name, Date date, ArrayList<Item> goods) {
        super(docType, name, date);
        for(Item good : goods) {
            this.goods.put(good, good.getCheckoutAmount());
            good.setCheckoutAmount(0);
        }
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
        Iterator<Item> goodKey = goods.keySet().iterator();
        while (goodKey.hasNext()) {
            Item good = goodKey.next();
            lines.add(good.getINVENTORY_NUMBER() + ";" + goods.get(good));
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
