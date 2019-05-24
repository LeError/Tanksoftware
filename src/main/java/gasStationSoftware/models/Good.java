package gasStationSoftware.models;

public class Good
extends Item {

    private StorageUnit storage;
    private int amount;
    private final String UNIT;

    public Good(ItemType type, float price, String currency, StorageUnit storage, int amount, String unit) {
        super(type, price, currency);
        this.storage = storage;
        this.amount = amount;
        this.storage.addItem(this);
        this.UNIT = unit;
    }

    public StorageUnit getStorage() {
        return storage;
    }

    public void setStorage(StorageUnit storage) {
        this.storage = storage;
    }

    public String getStorageUnit() {
        return (storage.getLabel() + " (" + storage.getX() + "|" + storage.getY() + ")");
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return UNIT;
    }

}