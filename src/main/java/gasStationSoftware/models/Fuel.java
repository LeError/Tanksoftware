package gasStationSoftware.models;

public class Fuel extends Item {

    private float amount;

    public Fuel(int inventoryNumber, String label, float price, String currency, float amount) {
        super(inventoryNumber, label, price, currency);
        this.amount = amount;
    }

    public float getAmount(){
        return amount;
    }

    public void addAmount(float amount){
        this.amount += amount;
    }

    public void removeAmount(float amount){
        this.amount -= amount;
    }
}