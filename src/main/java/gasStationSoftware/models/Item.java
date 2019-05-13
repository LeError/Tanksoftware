package gasStationSoftware.models;

public abstract class Item {

    protected final ItemType TYPE;
    private String currency;
    private float price;

    public Item(ItemType type, float price, String currency) {
        TYPE = type;
        this.price = price;
        this.currency = currency;
    }

    public int getINVENTORY_NUMBER() {
        return TYPE.getINVENTORY_NUMBER();
    }

    public ItemType getTYPE() {
        return TYPE;
    }

    public String getLABEL() {
        return TYPE.getLABEL();
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

    public String getINVENTORY_TYPE() {
        return TYPE.getTYPE_LABEL();
    }
}