package gasStationSoftware.models;

import java.util.ArrayList;

public class GasPump {

    private final int GAS_PUMP_NUMBER;
    private ArrayList<ItemType> fuels = new ArrayList<>();
    private ArrayList<FuelTank> tanks;

    private float checkoutAmount = 0;
    private Fuel checkoutFuel = null;

    /**
     * Constructor Zapfsäule
     * @param gasPumpNumber nummer der Säule
     * @param tanks liste der angeschlossenen tanks
     * @author Robin Herder
     */
    public GasPump(int gasPumpNumber, ArrayList<FuelTank> tanks) {
        this.GAS_PUMP_NUMBER = gasPumpNumber;
        this.tanks = tanks;
        for(FuelTank tank : tanks) {
            if(!fuels.contains(tank.getFuel())) {
                fuels.add(tank.getFuel());
            }
        }
    }

    /**
     * Kraftstoff der Zapfsäule hinzufügen
     * @param fuel fuel hinzufügen
     * @author Robin Herder
     */
    public void addFuel(ItemType fuel) {
        fuels.add(fuel);
    }

    /**
     * Kraftstoff der Zapfsäule entfernen
     * @param fuel fuel entfernen
     * @author Robin Herder
     */
    public void removeFuel(ItemType fuel) {
        fuels.remove(fuel);
    }

    /**
     * Gibt die Kraftstoffe der Zapfsäule zurück
     * @return fuels[]
     * @author Robin Herder
     */
    public ArrayList<ItemType> getFuels() {
        return fuels;
    }

    /**
     * Tank der Zapfsäule hinzufügen
     * @param tank tank hinzufügen
     * @author Robin Herder
     */
    public void addTank(FuelTank tank) {
        tanks.add(tank);
    }

    /**
     * Tank der Zapfsäule entfernen
     * @param tank tank entfernen
     * @author Robin Herder
     */
    public void removeTank(FuelTank tank){
        tanks.remove(tank);
    }

    /**
     * Gibt alle Tanks der Zapfsäule zurück
     * @return tanks[]
     * @author Robin Herder
     */
    public ArrayList<FuelTank> getTanks() {
        return tanks;
    }

    /**
     * Gibt die Zapfsäulennummer zurück
     * @return GAS_PUMP_NUMBER
     * @author Robin Herder
     */
    public int getGAS_PUMP_NUMBER() {
        return GAS_PUMP_NUMBER;
    }

    /**
     * Gibt die Kraftstoffe der Zapfsäule als String zurück
     * @return assigendFuels
     * @author Robin Herder
     */
    public String getAssignedFuels() {
        String assignedFuels = "";
        for (ItemType fuel : fuels) {
            assignedFuels += fuel.getINVENTORY_NUMBER() + " " + fuel.getLABEL() + "\n";
        }
        return assignedFuels;
    }

    /**
     * Gibt die Tanks der Zapfsäule als String zurück
     * @return assingedTanks
     * @author Robin Herder
     */
    public String getAssignedTanks() {
        String assignedTanks = "";
        for (FuelTank tank : tanks) {
            assignedTanks += tank.getTANK_NUMBER() + " " + tank.getFuelLabel() + "\n";
        }
        return assignedTanks;
    }

    /**
     * Setzen der amount im warenkorb
     * @param amount menge des kraftstoffs
     * @author Robin Herder
     */
    public void setCheckoutAmount(float amount) {
        checkoutAmount = amount;
    }

    /**
     * gibt menge des kraftstoffs in wk zurück
     * @return amount
     * @author Robin Herder
     */
    public float getCheckoutAmount() {
        return checkoutAmount;
    }

    /**
     * gibt das fuel im wk zurück
     * @return
     * @author Robin Herder
     */
    public Fuel getCheckoutFuel() {
        return checkoutFuel;
    }

    /**
     * setzt das fuel im wk
     * @param checkoutFuel kraftstoff
     * @author Robin Herder
     */
    public void setCheckoutFuel(Fuel checkoutFuel) {
        this.checkoutFuel = checkoutFuel;
    }

    /**
     * gibt label des kraftstoffs im warenkorb zurück
     * @return label
     * @author Robin Herder
     */
    public String getLabel() {
        return checkoutFuel.getLABEL();
    }

    /**
     * Stellt die Tanks der Zapfsäule ein
     * @param tanks die angeschlossenen tanks setzen
     * @author Robin Herder
     */
    public void setTanks(ArrayList<FuelTank> tanks) {
        this.tanks = tanks;
    }
}