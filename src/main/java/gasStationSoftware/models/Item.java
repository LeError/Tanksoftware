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
     * @author Lea Buchhold
     */
    public Item(ItemType type, float price, String currency) {
        TYPE = type;
        this.price = price;
        this.currency = currency;
    }

    /**
     * Gibt die Inventarnummer zurück
     * @return INVENTORY_NUMBER
     * @author Lea Buchhold
     */
    public int getINVENTORY_NUMBER() {
        return TYPE.getINVENTORY_NUMBER();
    }

    /**
     * Gibt den Itemtype des Items zurück
     * @return TYPE
     * @author Lea Buchhold
     */
    public ItemType getTYPE() {
        return TYPE;
    }

    /**
     * Gibt das Label des ItemType zurück
     * @return LABEL
     * @author Lea Buchhold
     */
    public String getLABEL() {
        return TYPE.getLABEL();
    }

    /**
     * Gibt die Währung zurück
     * @return currency
     * @author Lea Buchhold
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Gibt den Preis des Items zurück
     * @return price
     * @author Lea Buchhold
     */
    public float getPrice() {
        return price;
    }

    /**
     * Stellt den Preis des Items ein
     * @param price preis des items
     * @author Lea Buchhold
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gibt dem Inventartype des Items zurück
     * @return TYPE_LABEL
     * @author Lea Buchhold
     */
    public String getINVENTORY_TYPE() {
        return TYPE.getTYPE_LABEL();
    }

    /**
     * setzt die menge des produkts im wahrenkorb
     * @param amount menge im  wk
     * @author Lea Buchhold
     */
    public void setCheckoutAmount(float amount) {
        checkoutAmount = amount;
    }

    /**
     * erhöht die mänge des produkts im wahrenkorb
     * @param amount menge im wk
     * @author Lea Buchhold
     */
    public void addCheckoutAmount(float amount) {
        checkoutAmount += amount;
    }

    /**
     * gibt die menge des produkts im warenkorb zurück
     * @return checkoutAmount
     * @author Lea Buchhold
     */
    public float getCheckoutAmount() {
        return checkoutAmount;
    }
}