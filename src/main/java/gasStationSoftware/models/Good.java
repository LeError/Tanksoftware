package gasStationSoftware.models;

public class Good
extends Item {

    private int amount;
    private final String UNIT;

    /**
     * Constructor Ware
     * @param type
     * @param price
     * @param currency
     * @param amount
     * @param unit
     * @author Lea Buchhold
     */
    public Good(ItemType type, float price, String currency, int amount, String unit) {
        super(type, price, currency);
        this.amount = amount;
        this.UNIT = unit;
    }

    /**
     * Gibt die Menge der Ware zurück
     * @return amount
     * @author Lea Buchhold
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Fügt der aktuellen Menge hinzu
     * @param amount aount die hinzugefügt wird
     * @author Lea Buchhold
     */
    public void addAmount(int amount) {
        this.amount += amount;
    }

    /**
     * gibt die einheit zurück
     * @return unit
     * @author Lea Buchhold
     */
    public String getUNIT() {
        return UNIT;
    }

}