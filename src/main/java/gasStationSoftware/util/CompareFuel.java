package gasStationSoftware.util;

import gasStationSoftware.models.Fuel;

import java.util.Comparator;

public class CompareFuel implements Comparator<Fuel> {
    public int compare(Fuel fuelOne, Fuel fuelTwo) {
        return fuelOne.getINVENTORY_NUMBER() - fuelTwo.getINVENTORY_NUMBER();
    }
}
