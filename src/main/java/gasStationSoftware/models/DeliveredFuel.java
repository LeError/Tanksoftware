package gasStationSoftware.models;

public class DeliveredFuel extends Fuel {

    private final float AMOUNT;

    /**
     * Constructor DeliveredFuel
     * @param type
     * @param price
     * @param currency
     * @param amount
     * @author Robin Herder
     */
    public DeliveredFuel(ItemType type, float price, String currency, float amount) {
        super(type, price, currency);
        AMOUNT = amount;
    }

    /**
     * Gibt die Menge des bestellten Kraftstoffes zurück
     * @return
     * @author Robin Herder
     */
    public float getAmountDelivered() {
        return AMOUNT;
    }
}
