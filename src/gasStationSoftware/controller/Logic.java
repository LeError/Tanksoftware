package gasStationSoftware.controller;

import gasStationSoftware.exceptions.dataFileNotFound;
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
    private final String DATA_RECIPE_PATH = DATA_FILE_PATH + "\\recipes";
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
        loadDataFiles();
	}

    private void loadDataFiles() {
        checkDir(DATA_FILE_PATH);
        checkDir(DATA_RECIPE_PATH);
        try {
            checkDataFiles();
        } catch (IOException e) {
            //TODO
        }
        for (String file : DATA_FILE_NAMES) {
            try {
                createDefaultData(file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (gasStationSoftware.exceptions.dataFileNotFound dataFileNotFound) {
                //TODO
            }
        }
    }

    private void createDefaultData(String fileName) throws IOException, dataFileNotFound {
        String file = DATA_FILE_PATH + fileName;
        ArrayList<String> lines = new ArrayList();
        String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        if (ReadFile.isEmpty(file)) {
            switch (fileName) {
                case "goodsDelivery.txt":
                    lines.add("Lieferdatum=" + date);
                    lines.add("Warennummer;Bezeichnung;Lagereinheit;Menge;Einkaufspreis");
                    lines.add("000;TANKWare;Lizenz;1;9999.99");
                    break;
                case "goodsOrder.txt":
                    lines.add("Bestelldatum=" + date);
                    lines.add("Warennummer;Bestellmenge");
                    lines.add("000;00");
                    break;
                case "fuelDelivery.txt":
                    lines.add("LIEFERDATUM=" + date);
                    lines.add("DIESEL=0");
                    lines.add("DIESEL_PREIS=0.00");
                    lines.add("LKWDIESEL=0");
                    lines.add("LKWDIESEL_PREIS=0.00");
                    lines.add("SUPER=0");
                    lines.add("SUPER_PREIS=0.00");
                    lines.add("SUPERE10=0");
                    lines.add("SUPERE10_PREIS=0.00");
                    WriteFile.writeFile(lines, file);
                    break;
                case "fuelOrder.txt":
                    lines.add("BESTELLDATUM=" + date);
                    lines.add("DIESEL=0");
                    lines.add("LKWDIESEL=0");
                    lines.add("SUPER=0");
                    lines.add("SUPERE10=0");
                    WriteFile.writeFile(lines, file);
                    break;
                case "fuelInventory.txt":
                    break;
                case "goodsInventory.txt":
                    break;

                default:
                    throw new dataFileNotFound();
            }
        }
    }

    private void checkDir(String dir) {
        File dataDir = new File(dir);
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
