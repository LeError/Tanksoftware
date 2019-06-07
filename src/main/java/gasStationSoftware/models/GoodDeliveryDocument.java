package gasStationSoftware.models;

import java.util.ArrayList;
import java.util.Date;

public class GoodDeliveryDocument
extends Document {

    private final ArrayList<Good> GOODS;

    /**
     * Constructor GoodDeliveryDocument
     * @param docType art des dokuments
     * @param name name des dokuments
     * @param date erstellungsdatum
     * @param goods wahhren im doc
     * @author Robin Herder
     */
    public GoodDeliveryDocument(DocumentType docType, String name, Date date, ArrayList<Good> goods) {
        super(docType, name, date);
        GOODS = goods;
    }

    /**
     * Gibt inhalt zum schrieben der datei zurück
     * @return zeilen für datei
     * @author Robin Herder
     */
    @Override public ArrayList<String> getLinesForFile() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("Lieferdatum=" + getDATE());
        lines.add("Warennummer;Bezeichnung;Lagereinheit;Menge;Einkaufspreis");
        for(Good good : GOODS) {
            lines.add(good.getINVENTORY_NUMBER() + ";" + good.getLABEL() + ";" + good.getUNIT() + ";" + good.getAmount() + ";" + good.getPrice());
        }
        return lines;
    }

    /**
     * gibt die wahren in lieferung zurück
     * @return goods
     * @author Robin Herder
     */
    public ArrayList<Good> getGoods() {
        return GOODS;
    }

    /**
     * Gibt den Gesamtpreis des Waren zurück
     * @return total
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
     * Total formatiert für tab
     * @return total
     * @author Robin Herder
     */
    @Override public String getTotalForTab() {
        return "- " + getTotal();
    }

}
