package gasStationSoftware.controller;

import gasStationSoftware.ui.Window;

import java.io.File;
import java.io.IOException;

public class Logic {

    private final String DATA_FILE_PATH = System.getProperty("user.home") + "\\TANKWare\\";
    private final String[] DATA_FILE_NAMES = {
            "goodsDelivery.txt",
            "goodsOrder.txt",
            "fuelDelivery.txt",
            "fuelOrder.txt",
            "fuelInventory.txt",
            "goodsInventory.txt",
    };

	private Window window;

	public Logic(Window window) {
		this.window = window;
	}

    private void loadDataFiles() {
        checkDataDir();

    }

    private void checkDataDir() {
        File dataDir = new File(DATA_FILE_PATH);
        if (!dataDir.isDirectory()) {
            dataDir.mkdirs();
        }
    }

    private void checkDataFiles() throws IOException {
        for (String file : DATA_FILE_NAMES) {
            File dataFile = new File(DATA_FILE_PATH + file);
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
        }
    }

}
