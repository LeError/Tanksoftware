package gasStationSoftware.models;

import java.util.ArrayList;

public class GasPump {

    private final int GAS_PUMP_NUMBER;
    private ArrayList<ItemType> fuels = new ArrayList<>();
    private ArrayList<FuelTank> tanks;

    public GasPump(int gasPumpNumber, ArrayList<FuelTank> tanks) {
        this.GAS_PUMP_NUMBER = gasPumpNumber;
        this.tanks = tanks;
        for(FuelTank tank : tanks) {
            if(!fuels.contains(tank.getFuel())) {
                fuels.add(tank.getFuel());
            }
        }
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

    public String getAssignedFuels() {
        String assignedFuels = "";
        for (ItemType fuel : fuels) {
            assignedFuels += fuel.getINVENTORY_NUMBER() + " " + fuel.getLABEL() + "; ";
        }
        return assignedFuels;
    }

    public String getAssignedTanks() {
        String assignedTanks = "";
        for (FuelTank tank : tanks) {
            assignedTanks += tank.getTANK_NUMBER() + " " + tank.getFuelLabel() + "; ";
        }
        return assignedTanks;
    }

}