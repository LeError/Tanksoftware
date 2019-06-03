package gasStationSoftware.models;

public class DeliveredFuel extends Fuel {

    private final float AMOUNT;

    public DeliveredFuel(ItemType type, float price, String currency, float amount) {
        super(type, price, currency);
        AMOUNT = amount;
    }

    public float getAmountDelivered() {
        return AMOUNT;
    }
}
