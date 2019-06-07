package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class GoodOrderDocument
extends Document {

    private final ArrayList<Good> GOODS;
    private final ArrayList<Integer> GOODS_AMOUNT;

    /**
     * Constructor GoodOrderDocument
     *
     * @param docType     art des dokuments
     * @param name        name des dokuments
     * @param date        erstellungsdatum
     * @param goods       wahren im doc
     * @param goodsAmount wahrenanz im doc
     * @author Robin Herder
     */
    public GoodOrderDocument(DocumentType docType, String name, Date date, ArrayList<Good> goods,
    ArrayList<Integer> goodsAmount) {
        super(docType, name, date);
        GOODS = goods;
        GOODS_AMOUNT = goodsAmount;
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
        for (int i = 0; i < GOODS.size(); i++) {
            lines.add(GOODS.get(i).getINVENTORY_NUMBER() + ";" + GOODS_AMOUNT.get(i));
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
