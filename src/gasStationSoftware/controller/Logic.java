package gasStationSoftware.controller;

import gasStationSoftware.exceptions.dataFileNotFoundException;
import gasStationSoftware.ui.Window;
import gasStationSoftware.util.ReadFile;
import gasStationSoftware.util.WriteFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Logic {

    private final String DATA_FILE_PATH = System.getProperty("user.home") + "\\TANKWare\\";
    private final String[] DATA_SUB_PATHS = {
            DATA_FILE_PATH + "receipts",
            DATA_FILE_PATH + "fuelOrders",
            DATA_FILE_PATH + "fuelDeliveries",
            DATA_FILE_PATH + "goodsOrders",
            DATA_FILE_PATH + "goodsDeliveries"
    };

    private final String[] DATA_FILE_NAMES = {
            "tankwareData.json",
            "employees.txt"
    };

	private Window window;

	public Logic(Window window) {
		this.window = window;
        loadDataFiles();
	}

    private void loadDataFiles() {
        checkDir(DATA_FILE_PATH);
        checkDirs(DATA_SUB_PATHS);
        try {
            checkDataFiles();
        } catch (IOException e) {
            //TODO
        }
        for (String file : DATA_FILE_NAMES) {
            try {
                createDefaultData(file);
            } catch (IOException e) {
                //TODO
            } catch (dataFileNotFoundException e) {
                //TODO
            }
        }
    }

    private void createDefaultData(String fileName) throws IOException, dataFileNotFoundException {
        String file = DATA_FILE_PATH + fileName;
        String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        if (ReadFile.isEmpty(file)) {
            switch (fileName) {
                case "fuels.json":
                    break;
                case "goods.json":
                    break;
                case "fuelInventory.json":
                    break;
                case "goodsInventory.json":
                    break;
                case "employees.txt":
                    ArrayList<String> lines = new ArrayList();
                    lines.add("LASTUPDATE=" + date);
                    lines.add("EMPLOYEENR;FIRSTNAME;LASTNAME;EMPLOYMENTDATE");
                    lines.add("00000;Rolf;ADMIN;" + date);
                    WriteFile.writeFile(lines, file);
                    break;
                default:
                    throw new dataFileNotFoundException();
            }
        }
    }

    private void checkDir(String dir) {
        File dataDir = new File(dir);
        if (!dataDir.isDirectory()) {
            dataDir.mkdirs(); //TODO Add Exception
        }
    }

    private void checkDirs(String[] dirs) {
        for (String dir : dirs) {
            checkDir(dir);
        }
    }

    private void checkDataFiles() throws IOException {
        for (String file : DATA_FILE_NAMES) {
            File dataFile = new File(DATA_FILE_PATH + file);
            if (!dataFile.exists()) {
                dataFile.createNewFile();  //TODO if json load from resources //TODO Add Exception
            }
        }
    }

}