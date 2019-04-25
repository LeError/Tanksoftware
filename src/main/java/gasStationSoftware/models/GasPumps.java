package gasStationSoftware.models;

import java.util.ArrayList;

public class GasPumps {

    private final ArrayList<Fuels> FUELS;
    private final int GAS_PUMP_NUMBER;

    public GasPumps(ArrayList<Fuels> fuels, int gasPumpNumber) {
        this.FUELS = fuels;
        this.GAS_PUMP_NUMBER = gasPumpNumber;
    }

    public ArrayList<Fuels> getFUELS() {
        return FUELS;
    }

    public int getGAS_PUMP_NUMBER() {
        return GAS_PUMP_NUMBER;
    }
}