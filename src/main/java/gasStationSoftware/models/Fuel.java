package gasStationSoftware.models;

import gasStationSoftware.controller.Logic;

import java.util.ArrayList;

public class Fuel extends Item {

    private float amount;

    public Fuel(ItemType type, float price, String currency, float amount) {
        super(type, price, currency);
        this.amount = amount;
    }

    public float getAmount(){
        return amount;
    }

    public String getTanks() {
        String tanks = "";
        ArrayList<FuelTank> tankList = Logic.getInstance().getTanks();
        for (FuelTank tank : tankList) {
            if (tank.getFuel() == TYPE) {
                tanks += tank.getTANK_NUMBER() + " " + tank.getFuelLabel() + "\n";
            }
        }
        return tanks;
    }

    public void addAmount(float amount){
        this.amount += amount;
    }

    public void removeAmount(float amount){
        this.amount -= amount;
    }
}