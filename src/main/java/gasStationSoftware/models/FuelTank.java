package gasStationSoftware.models;

import gasStationSoftware.exceptions.NumberOutOfRangeException;

public class FuelTank {

    private final int TANK_NUMBER;
    private ItemType fuel;
    private float level, capacity;
    private float levelPercentage;

    /**
     * Constructor Kraftstofftank
     * @param tankNumber nummer des tanks
     * @param capacity kapazität des tanks
     * @param level stand des tanks
     * @param fuel kraftstoff in tank
     * @throws NumberOutOfRangeException
     * @author Robin Herder
     */
    public FuelTank(int tankNumber, float capacity, float level, ItemType fuel)
    throws NumberOutOfRangeException {
        TANK_NUMBER = tankNumber;
        this.capacity = capacity;
        this.fuel = fuel;
        this.level = level;
        levelPercentage = level / capacity;
        if(levelPercentage > 1 || levelPercentage < 0) {
            throw new NumberOutOfRangeException();
        }
    }

    /**
     * Gibt die Tanknummer zurück
     * @return TANK_NUMBER
     * @author Lea Buchhold
     */
    public int getTANK_NUMBER() {
        return TANK_NUMBER;
    }

    /**
     * Gibt die Kapazität zurück
     * @return capacity
     * @author Lea Buchhold
     */
    public float getCAPACITY() {
        return capacity;
    }

    /**
     * gibt label des kraftstoffs zurück
     * @return fuelLabel
     * @author Lea Buchhold
     */
    public String getFuelLabel() {
        return fuel.getLABEL();
    }

    /**
     * Gibt den Kraftstoff des Tanks zurück
     * @return fuel
     * @author Lea Buchhold
     */
    public ItemType getFuel() {
        return fuel;
    }

    /**
     * Kraftstoff des Tanks einstellen
     * @param fuel
     * @author Lea Buchhold
     */
    public void setFuel(ItemType fuel) {
        this.fuel = fuel;
    }

    /**
     * Gibt den Füllstand zurück
     * @return level
     * @author Lea Buchhold
     */
    public float getLevel() {
        return level;
    }

    /**
     * Gibt den Füllstand in Prozent zurück
     * @return levelPercentage
     * @author Lea Buchhold
     */
    public float getLevelPercentage() {
        return levelPercentage * 100;
    }

    /**
     * Neuen Füllstand des Tanks festlegen
     * @param level
     * @throws NumberOutOfRangeException
     * @author Robin Herder
     */
    public void addLevel(float level) throws NumberOutOfRangeException {
        this.level += level;
        levelPercentage = this.level / capacity;
        if(levelPercentage > 1 || levelPercentage < 0) {
            throw new NumberOutOfRangeException();
        }
    }

    /**
     * Stellt das Füllstandlevel des Tanks ein
     * @param level
     * @throws NumberOutOfRangeException
     * @author Robin Herder
     */
    public void setLevel(float level)
    throws NumberOutOfRangeException {
        this.level = level;
        levelPercentage = this.level / capacity;
        if (levelPercentage > 1 || levelPercentage < 0) {
            throw new NumberOutOfRangeException();
        }
    }

    /**
     * Stellt die Kapazität ein
     * @param capacity
     * @author Lea Buchhold
     */
    public void setCAPACITY(float capacity) {
        this.capacity = capacity;
    }

    /**
     * Gibt InvNumber des Tanks zurück
     * @return invNumber
     * @author Lea Buchhold
     */
    public int getInvNumber() {
        return fuel.getINVENTORY_NUMBER();
    }

    /**
     * Gibt die freie Kapazität des Tanks zurück
     * @return freeSpace
     * @author Lea Buchhold
     */
    public float getFreeSpace() {
        return capacity - level;
    }

    /**
     * Gibt prozentfüllstand für tabelle zurück
     * @return freeSpace
     * @author Robin Herder
     */
    public double getLevelPercentageProgress() {
        return levelPercentage;
    }
}