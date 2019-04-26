package gasStationSoftware.models;

public class Fuels extends Items {

    private float amount = 0;

    public Fuels(int inventoryNumber, String label, float price, String currency) {
        super(inventoryNumber, label, price, currency);
    }
}