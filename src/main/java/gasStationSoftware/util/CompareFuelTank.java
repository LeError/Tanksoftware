package gasStationSoftware.util;

import gasStationSoftware.models.FuelTank;

import java.util.Comparator;

public class CompareFuelTank implements Comparator<FuelTank> {
    public int compare(FuelTank fuelTankOne, FuelTank fuelTankTwo) {
        return fuelTankOne.getTANK_NUMBER() - fuelTankTwo.getTANK_NUMBER();
    }
}
