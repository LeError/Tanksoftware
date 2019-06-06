package gasStationSoftware.models;

public abstract class Item {

    protected final ItemType TYPE;
    private String currency;
    private float price;
    private float checkoutAmount = 0;

    /**
     * Constructor Item
     * @param type ItemType des Items
     * @param price Preis des Items
     * @param currency Währung des Preises des Items
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
     * Gibt das Label des ItemType zurück
     * @return LABEL
     * @author Robin Herder
     */
    public String getLABEL() {
        return TYPE.getLABEL();
    }

    /**
     * Gibt die Währung zurück
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
     * @param price preis des items
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
     * setzt die menge des produkts im wahrenkorb
     * @param amount menge im  wk
     * @author Robin Herder
     */
    public void setCheckoutAmount(float amount) {
        checkoutAmount = amount;
    }

    /**
     * erhöht die mänge des produkts im wahrenkorb
     * @param amount menge im wk
     * @author Robin Herder
     */
    public void addCheckoutAmount(float amount) {
        checkoutAmount += amount;
    }

    /**
     * gibt die menge des produkts im warenkorb zurück
     * @return checkoutAmount
     * @author Robin Herder
     */
    public float getCheckoutAmount() {
        return checkoutAmount;
    }
}