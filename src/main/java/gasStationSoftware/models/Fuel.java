package gasStationSoftware.models;

public class Fuel extends Item {

    private float amount = 0;

    public Fuel(int inventoryNumber, String label, float price, String currency) {
        super(inventoryNumber, label, price, currency);
    }
}