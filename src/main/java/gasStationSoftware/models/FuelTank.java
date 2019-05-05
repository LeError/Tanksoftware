package gasStationSoftware.models;

import gasStationSoftware.exceptions.NumberOutOfRangeException;

public class FuelTank {

    private final int TANK_NUMBER;
    private final float CAPACITY;
    private ItemType fuel;
    private float level;
    private float levelPercentage;

    public FuelTank(int tankNumber, float capacity, float level, ItemType fuel)
    throws NumberOutOfRangeException {
        TANK_NUMBER = tankNumber;
        CAPACITY = capacity;
        this.fuel = fuel;
        this.level = level;
        levelPercentage = level / capacity;
        if(levelPercentage > 1 || levelPercentage < 0) {
            throw new NumberOutOfRangeException();
        }
    }

    public int getTANK_NUMBER() {
        return TANK_NUMBER;
    }

    public float getCAPACITY() {
        return CAPACITY;
    }

    public String getFuelLabel() {
        return fuel.getLABEL();
    }

    public ItemType getFuel() {
        return fuel;
    }

    public void setFuel(ItemType fuel) {
        this.fuel = fuel;
    }

    public float getLevel() {
        return level;
    }

    public float getLevelPercentage() {
        return levelPercentage;
    }

    public void setLevel(float level) throws NumberOutOfRangeException {
        this.level = level;
        levelPercentage = level / CAPACITY;
        if(levelPercentage > 1 || levelPercentage < 0) {
            throw new NumberOutOfRangeException();
        }
    }

    public int getInvNumber() {
        return fuel.getINVENTORY_NUMBER();
    }
}