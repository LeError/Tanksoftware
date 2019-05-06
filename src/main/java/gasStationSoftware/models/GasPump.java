package gasStationSoftware.models;

import java.util.ArrayList;

public class GasPump {

    private final int GAS_PUMP_NUMBER;
    private ArrayList<ItemType> fuels;
    private ArrayList<FuelTank> tanks;

    public GasPump(ArrayList<ItemType> fuels, int gasPumpNumber, ArrayList<FuelTank> tanks) {
        this.GAS_PUMP_NUMBER = gasPumpNumber;
        this.fuels = fuels;
        this.tanks = tanks;
    }

    public void addFuel(ItemType fuel) {
        fuels.add(fuel);
    }

    public void removeFuel(ItemType fuel) {
        fuels.remove(fuel);
    }

    public ArrayList<ItemType> getFuels() {
        return fuels;
    }

    public void addTank(FuelTank tank) {
        tanks.add(tank);
    }

    public void removeTank(FuelTank tank){
        tanks.remove(tank);
    }

    public ArrayList<FuelTank> getTanks() {
        return tanks;
    }

    public int getGAS_PUMP_NUMBER() {
        return GAS_PUMP_NUMBER;
    }
}