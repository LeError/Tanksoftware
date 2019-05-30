package gasStationSoftware.models;

import gasStationSoftware.controller.Logic;

import java.util.ArrayList;

public class Fuel extends Item {

    /**
     * Constuctor Fuel
     * @param type
     * @param price
     * @param currency
     * @author Robin Herder
     */
    public Fuel(ItemType type, float price, String currency) {
        super(type, price, currency);
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

    private ArrayList<FuelTank> getOTanks() {
        ArrayList<FuelTank> tanks = new ArrayList<>();
        ArrayList<FuelTank> tankList = Logic.getInstance().getTanks();
        for (FuelTank tank : tankList) {
            if (tank.getFuel() == TYPE) {
                tanks.add(tank);
            }
        }
        return tanks;
    }

    /**
     * Gibt die aktuelle Menge des Kraftstoffs zurück
     * @return amount
     * @author Robin Herder
     */
    public float getAmount(){
        int amount = 0;
        for(FuelTank tank : getOTanks()) {
            amount += tank.getLevel();
        }
        return amount;
    }

    public boolean checkSpace(float amount) {
        int free = 0;
        for(FuelTank tank : getOTanks()) {
            free += tank.getCAPACITY() - tank.getLevel();
        }
        if(amount > free) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Neuen Kraftstoff hinzufügen
     * @param amount
     * @author Robin Herder
     */
    public void addAmount(float amount) throws Exception {
        boolean raiseError = !checkSpace(amount);
        for(FuelTank tank : getOTanks()) {
            if(tank.getFreeSpace() == 0){
                continue;
            } else if(amount < tank.getFreeSpace()) {
                tank.addLevel(amount);
                break;
            } else {
                float add = tank.getFreeSpace();
                tank.addLevel(add);
                amount -= add;
            }
        }
        if(raiseError) {
            throw new Exception("Nicht genug platz in den tanks für die menge " + amount + ". Die Tanks werden bis zu ihrer Kapazitätsgrenze gefüllt!");
        }
    }

    /**
     * Kraftstoff entnehmen
     * @param amount
     * @author Robin Herder
     */
    public void removeAmount(float amount) throws Exception {
        boolean raiseError = getAmount() < amount;
        for(FuelTank tank : getOTanks()) {
            if(tank.getLevel() == 0) {
                continue;
            } else if(tank.getLevel() >= amount) {
                tank.addLevel(- amount);
                break;
            } else {
                float remove = tank.getLevel();
                tank.addLevel(- remove);
                amount -= remove;
            }
        }
        if(raiseError) {
            throw new Exception("Nicht genug Kraftstoff in den tanks! Die Tanks werden bis zu ihrer Kapazitätsgrenze belastet!");
        }
    }
}