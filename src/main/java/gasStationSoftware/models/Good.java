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
     * @return
     * @author Robin Herder
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Fügt der aktuellen Menge hinzu
     * @param amount
     * @author Robin Herder
     */
    public void addAmount(int amount) {
        this.amount += amount;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    public String getUNIT() {
        return UNIT;
    }

}