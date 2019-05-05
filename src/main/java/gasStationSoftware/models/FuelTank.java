package gasStationSoftware.models;

import gasStationSoftware.exceptions.NumberOutOfRangeException;

public class FuelTank {

    private final int TANK_NUMBER;
    private final float CAPACITY;
    private ItemType fuel;
    private float level;

    public FuelTank(int tankNumber, float capacity, float level, ItemType fuel)
    throws NumberOutOfRangeException {
        if (level < 0 || level > 1) {
            throw new NumberOutOfRangeException("Only values between 0 and 1 are allowed!");
        }
        TANK_NUMBER = tankNumber;
        CAPACITY = capacity;
        this.fuel = fuel;
        this.level = level;
    }

    public int getTANK_NUMBER() {
        return TANK_NUMBER;
    }

    public float getCAPACITY() {
        return CAPACITY;
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

    public void setLevel(float level) {
        this.level = level;
    }
}