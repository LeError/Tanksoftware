package gasStationSoftware.models;

import gasStationSoftware.controller.Logic;

import java.util.ArrayList;

public class Fuel extends Item {

    private GasPump checkoutTankNumber = null;

    /**
     * Constuctor Fuel
     * @param type ItemType des Krafrstoffes
     * @param price Preis des Kraftstoffes
     * @param currency Währung des Preises des Kraftstoffes
     * @author Robin Herder
     */
    public Fuel(ItemType type, float price, String currency) {
        super(type, price, currency);
    }

    /**
     * Liefert Tanks formatiert für Table
     * @return tanks
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
     * Gibt die Tanks zurück als liste
     * @return tanks
     * @author Robin Herder
     */
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

    /**
     * Überprüft die freie Kapazität
     * @param amount menge die in tank soll
     * @return Wahrheitswert
     * @author Robin Herder
     */
    public boolean checkSpace(float amount) {
        int free = 0;
        for(FuelTank tank : getOTanks()) {
            free += tank.getCAPACITY() - tank.getLevel();
        }
        if(amount <= free) {
            return true;
        }
        return false;
    }

    /**
     * Neuen Kraftstoff hinzufügen
     * @param amount menge hhinzufügen
     * @author Robin Herder
     */
    public void addAmount(float amount) throws Exception {
        boolean raiseError = !checkSpace(amount);
        for(FuelTank tank : getOTanks()) {
            if(amount == 0) {
                break;
            }
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
     * @param amount menge entfernen
     * @author Robin Herder
     */
    public void removeAmount(float amount, GasPump gasPump) throws Exception {
        boolean raiseError = getAmount() < amount;
        ArrayList<FuelTank> tanks = new ArrayList<>();
        for(FuelTank tank : gasPump.getTanks()) {
            if(tank.getFuel() == getTYPE()) {
                tanks.add(tank);
            }
        }
        for(FuelTank tank : tanks) {
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

    /**
     * Gibt den Checkouttank zurück
     * @return pump
     * @author Robin Herder
     */
    public GasPump getCheckoutTank() {
        return checkoutTankNumber;
    }

    /**
     * Stellt den CheckoutTank ein
     * @param checkoutTankNumber setzten der säule für checkout
     * @author Robin Herder
     */
    public void setCheckoutTank(GasPump checkoutTankNumber) {
        this.checkoutTankNumber = checkoutTankNumber;
    }
}