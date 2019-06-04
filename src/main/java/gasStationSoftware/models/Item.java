package gasStationSoftware.models;

public abstract class Item {

    protected final ItemType TYPE;
    private String currency;
    private float price;
    private float checkoutAmount = 0;

    /**
     * Constructor Item
     * @param type
     * @param price
     * @param currency
     * @author Robin Herder
     */
    public Item(ItemType type, float price, String currency) {
        TYPE = type;
        this.price = price;
        this.currency = currency;
    }

    /**
     * Gibt die Inventarnummer zurück
     * @return INVENTORY_NUMBER
     * @author Robin Herder
     */
    public int getINVENTORY_NUMBER() {
        return TYPE.getINVENTORY_NUMBER();
    }

    /**
     * Gibt den Itemtype des Items zurück
     * @return TYPE
     * @author Robin Herder
     */
    public ItemType getTYPE() {
        return TYPE;
    }

    /**
     *
     * @return LABEL
     * @author Robin Herder
     */
    public String getLABEL() {
        return TYPE.getLABEL();
    }

    /**
     *
     * @return currency
     * @author Robin Herder
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gibt den Preis des Items zurück
     * @return price
     * @author Robin Herder
     */
    public float getPrice() {
        return price;
    }

    /**
     * Stellt den Preis des Items ein
     * @param price
     * @author Robin Herder
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gibt dem Inventartype des Items zurück
     * @return TYPE_LABEL
     * @author Robin Herder
     */
    public String getINVENTORY_TYPE() {
        return TYPE.getTYPE_LABEL();
    }

    /**
     *
     * @param amount
     * @author Robin Herder
     */
    public void setCheckoutAmount(float amount) {
        checkoutAmount = amount;
    }

    /**
     *
     * @param amount
     * @author Robin Herder
     */
    public void addCheckoutAmount(float amount) {
        checkoutAmount += amount;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    public float getCheckoutAmount() {
        return checkoutAmount;
    }
}