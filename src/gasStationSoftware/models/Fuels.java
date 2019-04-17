package gasStationSoftware.models;

public class Fuels
extends Items {

	private float amount = 0;

	public Fuels(int inventoryNumber, String label, float price, String currency, FuelTank[] tanks) {
		super(inventoryNumber, label, price, currency);
		getAmount(tanks);
	}

	private void getAmount(FuelTank[] tanks) {
		for (FuelTank tank : tanks) {
			amount += tank.getCAPACITY() * tank.getLevel();
		}
	}

	public float getAmount() {
		return amount;
	}

}