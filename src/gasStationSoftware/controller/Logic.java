package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.util.ReadFile;
import gasStationSoftware.util.WriteFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Logic {

    private final String DATA_FILE_PATH = System.getProperty("user.home") + "\\TANKWare\\";
    private final String[] DATA_SUB_PATHS = {
            DATA_FILE_PATH + "receipts",
            DATA_FILE_PATH + "fuelOrders",
            DATA_FILE_PATH + "fuelDeliveries",
            DATA_FILE_PATH + "goodsOrders", DATA_FILE_PATH + "goodsDeliveries", DATA_FILE_PATH + "themes"
    };

    private final String[] DATA_FILE_NAMES = { "inventory.json", "settings.json",
            "employees.txt"
    };

    private WindowController windowController;

    public Logic(WindowController windowController) {
        this.windowController = windowController;
        loadDataFiles();
	}

    private void loadDataFiles() {
        try {
			checkDir(DATA_FILE_PATH);
			checkDirs(DATA_SUB_PATHS);
            checkDataFiles();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataFileNotFoundException e) {
			e.printStackTrace();
		}

    }

	private void createDefaultData(String fileName)
	throws DataFileNotFoundException {
        String file = DATA_FILE_PATH + fileName;
        if (ReadFile.isEmpty(file)) {
            switch (fileName) {
			case "tankwareData.json":
                    break;
                case "employees.txt":
                    ArrayList<String> lines = new ArrayList();
					String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
                    lines.add("LASTUPDATE=" + date);
                    lines.add("EMPLOYEENR;FIRSTNAME;LASTNAME;EMPLOYMENTDATE");
                    lines.add("00000;Rolf;ADMIN;" + date);
                    WriteFile.writeFile(lines, file);
                    break;
                default:
                    throw new DataFileNotFoundException();
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

	private void checkDataFiles()
	throws IOException, DataFileNotFoundException {
        for (String file : DATA_FILE_NAMES) {
            File dataFile = new File(DATA_FILE_PATH + file);
			if (!dataFile.exists() && !FilenameUtils.getExtension(file).equals("json")) {
				dataFile.createNewFile();
			} else if (!dataFile.exists() && FilenameUtils.getExtension(file).equals("json")) {
				switch (file) {
				case "inventory.json":
					writeJSONResources("inventoryDefault.json", "inventory.json");
					break;
				case "settings.json":
					writeJSONResources("settings.json", "settings.json");
					break;
				default:
					throw new DataFileNotFoundException(file);
				}
            }
        }
    }

	private void writeJSONResources(String source, String file) {
		InputStream jsonSource = getClass().getClassLoader().getResourceAsStream("\\json\\" + source);
		File jsonDestination = new File(DATA_FILE_PATH + file);
		try {
			FileUtils.copyInputStreamToFile(jsonSource, jsonDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}