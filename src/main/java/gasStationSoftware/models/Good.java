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
     * @author Robin Herder
     */
    public Good(ItemType type, float price, String currency, int amount, String unit) {
        super(type, price, currency);
        this.amount = amount;
        this.UNIT = unit;
    }

    /**
     * Gibt die Menge der Ware zurück
     * @return amount
     * @author Robin Herder
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Fügt der aktuellen Menge hinzu
     * @param amount aount die hinzugefügt wird
     * @author Robin Herder
     */
    public void addAmount(int amount) {
        this.amount += amount;
    }

    /**
     * gibt die einheit zurück
     * @return unit
     * @author Robin Herder
     */
    public String getUNIT() {
        return UNIT;
    }

}