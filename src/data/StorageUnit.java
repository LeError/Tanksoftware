package data;

import java.awt.*;

public class StorageUnit {

	private final String LABEL;
	private final Point LOCATION;

	public StorageUnit(String label, int x, int y) {
		LABEL = label;
		LOCATION = new Point(x, y);
	}

}
