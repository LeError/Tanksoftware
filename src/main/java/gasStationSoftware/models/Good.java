package gasStationSoftware.models;

public class Good
extends Item {

    private StorageUnit storage;
    private int amount;
    private final String UNIT;

    /**
     * Constructor Ware
     * @param type
     * @param price
     * @param currency
     * @param storage
     * @param amount
     * @param unit
     * @author Robin Herder
     */
    public Good(ItemType type, float price, String currency, StorageUnit storage, int amount, String unit) {
        super(type, price, currency);
        this.storage = storage;
        this.amount = amount;
        this.storage.addItem(this);
        this.UNIT = unit;
    }

    /**
     *
     * @return storage
     * @author Robin Herder
     */
    public StorageUnit getStorage() {
        return storage;
    }

    /**
     *
     * @param storage
     * @author Robin Herder
     */
    public void setStorage(StorageUnit storage) {
        this.storage = storage;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    public String getStorageUnit() {
        return (storage.getLabel() + " (" + storage.getX() + "|" + storage.getY() + ")");
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
     *
     * @return
     * @author Robin Herder
     */
    public String getUNIT() {
        return UNIT;
    }

}