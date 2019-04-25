package gasStationSoftware.models;

public class Goods
extends Items {

    private StorageUnit storage;
    private int amount;

    public Goods(int inventoryNumber, String label, float price, String currency, StorageUnit storage, int amount) {
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