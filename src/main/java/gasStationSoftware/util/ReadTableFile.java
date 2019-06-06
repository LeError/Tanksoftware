package gasStationSoftware.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadTableFile {

    private final File FILE;
    private final ArrayList<String> LINES = new ArrayList<>();
    private final ArrayList<String> TOP_LINES = new ArrayList<>();

    /**
     * Constructor ReadFile
     * @param url pfad der zu lesenden Datei
     * @author Robin Herder
     */
    public ReadTableFile(String url) { //TODO add canRead & canWrite
        FILE = new File(url);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(";")) {
                    LINES.add(line);
                } else {
                    TOP_LINES.add(line);
                }
            }
            LINES.remove(0);
        } catch (Exception e) {
            System.err.println("[FileIn]:\nCan't read File. Something went wrong!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 2D Array des Zerlegten Inhalts
     * @return lines[][]
     * @author Robin Herder
     */
    public String[][] getLINES() {
        String[][] lines = new String[LINES.size()][];
        for (int i = 0; i < LINES.size(); i++) {
            lines[i] = getLine(LINES.get(i));
        }
        return lines;
    }

    /**
     * Liste der Legende
     * @return TOP_LINES[]
     * @author Robin Herder
     */
    public ArrayList<String> getTOP_LINES() {
        return TOP_LINES;
    }

    /**
     * Zeile an ; zerlegen
     * @param line Zeile die getrennt werden soll
     * @return Line[]
     */
    private String[] getLine(String line) {
        return line.trim().split(";");
    }

    /**
     * Gibt das Datum zurück
     * @return date
     * @author Robin Herder
     */
    public Date getDate() {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(TOP_LINES.get(0).trim().split("=")[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}