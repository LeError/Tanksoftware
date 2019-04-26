package gasStationSoftware.models;

import java.util.ArrayList;

public class GasPumps {

    private final int GAS_PUMP_NUMBER;
    private ArrayList<Fuels> fuels;
    private ArrayList<FuelTank> tanks;

    public GasPumps(ArrayList<Fuels> fuels, int gasPumpNumber, ArrayList<FuelTank> tanks) {
        this.GAS_PUMP_NUMBER = gasPumpNumber;
        this.fuels = fuels;
        this.tanks = tanks;
    }

    public void addFuel(Fuels fuel) {
        fuels.add(fuel);
    }

    public void removeFuel(Fuels fuel){
        fuels.remove(fuel);
    }

    public ArrayList<Fuels> getFuels() {
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