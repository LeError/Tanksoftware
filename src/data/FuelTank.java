package data;

public class FuelTank {

	private final int TANK_NUMBER;
	private final float CAPACITY;
	private float level;

	public FuelTank(int tankNumber, float capacity, float level) {
		TANK_NUMBER = tankNumber;
		CAPACITY = capacity;
		this.level = level;
	}

	public int getTANK_NUMBER() {
		return TANK_NUMBER;
	}

	public float getCAPACITY() {
		return CAPACITY;
	}

	public float getLevel() {
		return level;
	}

	public void setLevel(float level) {
		this.level = level;
	}
}
