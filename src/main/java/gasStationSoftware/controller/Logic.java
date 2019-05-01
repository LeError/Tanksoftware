package gasStationSoftware.controller;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import gasStationSoftware.util.ReadJSON;
import gasStationSoftware.util.WriteFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logic {

    private static Logic logic;
    private static WindowController windowController;

    private final String DATA_FILE_PATH = System.getProperty("user.home") + "\\TANKWare\\";
    private final String[] DATA_SUB_PATHS = {
            DATA_FILE_PATH + "receipts\\",
            DATA_FILE_PATH + "fuelOrders\\",
            DATA_FILE_PATH + "fuelDeliveries\\",
            DATA_FILE_PATH + "goodsOrders\\",
            DATA_FILE_PATH + "goodsDeliveries\\",
            DATA_FILE_PATH + "themes\\"
    };

    private final String[] DATA_FILE_NAMES = {
            "inventory.json",
            "settings.json",
            "themes\\default.json",
            "employees.txt"
    };

    private Logic() {
        checkDir(DATA_FILE_PATH);
        checkDirs(DATA_SUB_PATHS);
        try {
            checkDataFiles();
        } catch (IOException e) {
            displayError("Could not write file to filesystem !", e, true);
        } catch (DataFileNotFoundException e) {
            displayError("Could not find required data file!", e, true);
        }
    }

    public static Logic getInstance() {
        if (logic == null) {
            logic = new Logic();
        }
        return logic;
    }

    public static Logic getInstance(WindowController windowController) {
        Logic.windowController = windowController;
        return getInstance();
    }

    private void checkDir(String dir) {
        File dataDir = new File(dir);
        if (!dataDir.isDirectory()) {
            dataDir.mkdirs();
        }
    }

    private void checkDirs(String[] dirs) {
        for (String dir : dirs) {
            checkDir(dir);
        }
    }

    private void checkDataFiles() throws IOException, DataFileNotFoundException {
        for (String file : DATA_FILE_NAMES) {
            File dataFile = new File(DATA_FILE_PATH + file);
            if (!dataFile.exists() && !FilenameUtils.getExtension(file).equals("json")) {
                WriteFile write = new WriteFile(dataFile.toString());
                write.addLine("LASTUPDATE=" + getDate());
                write.addLine("EMPLOYEENR;FIRSTNAME;LASTNAME;EMPLOYMENTDATE");
                write.addLine("00000;ADMIN;ADMIN;" + getDate());
                write.write();
            } else if (!dataFile.exists() && FilenameUtils.getExtension(file).equals("json")) {
                switch (file) {
                case "inventory.json":
                    exportJSONResources("inventoryDefault.json", "inventory.json");
                    break;
                case "settings.json":
                    exportJSONResources("settings.json", "settings.json");
                    break;
                case "themes\\default.json":
                    exportJSONResources("defaultTheme.json", "themes\\default.json");
                    break;
                default:
                    throw new DataFileNotFoundException(file);
                }
            }
        }
    }

    private void exportJSONResources(String source, String file) {
        InputStream jsonSource = getClass().getClassLoader().getResourceAsStream("\\json\\" + source);
        File jsonDestination = new File(DATA_FILE_PATH + file);
        try {
            FileUtils.copyInputStreamToFile(jsonSource, jsonDestination);
        } catch (IOException e) {
            displayError("Could not export data file from jar!", e, true);
        }
    }

    private static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 )
        );
    }

    public static void displayError(String error, Exception e, boolean end) {
        JOptionPane.showMessageDialog(null, error + "\n" + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        if(end){
            System.exit(-1);
        }
    }

    public void loadFiles() {
        try {
            loadTheme();
        } catch (DataFileNotFoundException e) {
            displayError("Can't load theme save file!", e, true);
        }
    }

    private void loadTheme() throws DataFileNotFoundException {
        ReadJSON read = new ReadJSON(DATA_FILE_PATH + DATA_FILE_NAMES[1]);
        String theme = read.getItemString("theme");
        windowController.setComboboxThemes(read.getItemStringArray("themes"));
        read = new ReadJSON(DATA_SUB_PATHS[5] + theme + ".json");
        windowController.setTheme(
                hex2Rgb(read.getItemString("menuBar")),
                hex2Rgb(read.getItemString("contentPaneBackground")),
                hex2Rgb(read.getItemString("icons")),
                hex2Rgb(read.getItemString("dividerMenuBar")),
                hex2Rgb(read.getItemString("fontContent")),
                hex2Rgb(read.getItemString("buttonsBackground")),
                hex2Rgb(read.getItemString("buttonsFont")),
                hex2Rgb(read.getItemString("dividerContent"))
        );
    }

}