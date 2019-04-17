package gasStationSoftware.models;

public abstract class Items {

    private final int INVENTORY_NUMBER;
    private String label, currency;
    private float price;

    public Items(int inventoryNumber, String label, float price, String currency) {
        INVENTORY_NUMBER = inventoryNumber;
        this.label = label;
        this.price = price;
        this.currency = currency;
    }

    public int getINVENTORY_NUMBER() {
        return INVENTORY_NUMBER;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCurrency() {
        return currency;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
