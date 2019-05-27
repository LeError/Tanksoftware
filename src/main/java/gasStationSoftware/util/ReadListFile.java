package gasStationSoftware.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReadListFile {

    private final File FILE;
    private String date;
    private final ArrayList<String> LINES = new ArrayList<>();

    public ReadListFile(String url) {
        FILE = new File(url);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE))) {
            String line;
            boolean firstLine = true;
            date = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                LINES.add(line);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String[][] getLINES() {
        String[][] lines = new String[LINES.size()][2];
        for(int i = 0; i < LINES.size(); i++) {
            lines[i] = getLine(LINES.get(i));
        }
        return lines;
    }

    private String[] getLine(String line) {
        return line.trim().split("=");
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(getLine(date)[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
