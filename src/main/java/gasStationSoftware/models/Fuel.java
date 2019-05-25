package gasStationSoftware.models;

import gasStationSoftware.controller.Logic;

import java.util.ArrayList;

public class Fuel extends Item {

    private float amount;

    /**
     * Constuctor Fuel
     * @param type
     * @param price
     * @param currency
     * @param amount
     * @author Robin Herder
     */
    public Fuel(ItemType type, float price, String currency, float amount) {
        super(type, price, currency);
        this.amount = amount;
    }

    /**
     * Gibt die aktuelle Menge des Kraftstoffs zurück
     * @return amount
     * @author Robin Herder
     */
    public float getAmount(){
        return amount;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
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

    /**
     * Neuen Kraftstoff hinzufügen
     * @param amount
     * @author Robin Herder
     */
    public void addAmount(float amount){
        this.amount += amount;
    }

    /**
     * Kraftstoff entnehmen
     * @param amount
     * @author Robin Herder
     */
    public void removeAmount(float amount){
        this.amount -= amount;
    }
}