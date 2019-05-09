package gasStationSoftware.util;

import gasStationSoftware.models.*;

import java.util.Comparator;

public class CompareModel implements Comparator<ItemType> {

    public int compare(ItemType iTypeOne, ItemType iTypeTwo) {
        return iTypeOne.getINVENTORY_NUMBER() - iTypeTwo.getINVENTORY_NUMBER();
    }

    public int compare(FuelTank fuelTankOne, FuelTank fuelTankTwo) {
        return fuelTankOne.getTANK_NUMBER() - fuelTankTwo.getTANK_NUMBER();
    }

    public int compare(GasPump gasPumpOne, GasPump gasPumpTwo) {
        return gasPumpOne.getGAS_PUMP_NUMBER() - gasPumpTwo.getGAS_PUMP_NUMBER();
    }

    public int compare(Fuel fuelOne, Fuel fuelTwo) {
        return fuelOne.getINVENTORY_NUMBER() - fuelTwo.getINVENTORY_NUMBER();
    }

    public int compare(Good goodOne, Good goodTwo) {
        return goodOne.getINVENTORY_NUMBER() - goodTwo.getINVENTORY_NUMBER();
    }

    public int compare(Employee employeeOne, Employee employeeTwo) {
        return employeeOne.getEMPLOYEE_NUMBER() - employeeTwo.getEMPLOYEE_NUMBER();
    }

}