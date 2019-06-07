package gasStationSoftware.models;

public class DeliveredFuel extends Fuel {

    private final float AMOUNT;

    /**
     * Constructor DeliveredFuel (Hilfsklasse)
     * @param type ItemType des  Fuels
     * @param price Preis des Fuels
     * @param currency Währung des Preises
     * @param amount menge des Kraftstoffes
     * @author Lea Buchhold
     */
    public DeliveredFuel(ItemType type, float price, String currency, float amount) {
        super(type, price, currency);
        AMOUNT = amount;
    }

    /**
     * Gibt die Menge des bestellten Kraftstoffes zurück
     * @return amount
     * @author Lea Buchhold
     */
    public float getAmountDelivered() {
        return AMOUNT;
    }
}
