package gasStationSoftware.models;

public class Good
extends Item {

    private StorageUnit storage;
    private int amount;

    public Good(int inventoryNumber, String label, float price, String currency, StorageUnit storage, int amount) {
        super(inventoryNumber, label, price, currency);
        this.storage = storage;
        this.amount = amount;
        this.storage.addItem(this);
    }

    public StorageUnit getStorage() {
        return storage;
    }

    public void setStorage(StorageUnit storage) {
        this.storage = storage;
    }
}