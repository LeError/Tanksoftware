package gasStationSoftware.util;

import gasStationSoftware.controller.Logic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WriteFile {

    private final Path FILE;
    private ArrayList<String> lines = new ArrayList<>();

    /**
     * Constructor WriteFile
     * @param path Pfad der zu schreibenden datei
     * @author Robin Herder
     */
    public WriteFile(String path){
        FILE = Paths.get(path);
    }

    /**
     * Fügt eine neue Linie der ArrayListe hinzu
     * @param line String der zu schreiben ist
     * @author Robin Herder
     */
    public void addLine(String line) {
        lines.add(line);
    }

    /**
     * Schreibt die Datei mit dem Inhalt der ArrayList
     * @throws IOException
     * @author Robin Herder
     */
    public void write() throws IOException {
        try {
            Files.write(FILE, lines, Charset.forName("UTF-8"));
        } catch(Exception e) {
            Logic.displayError("Kann Datei nicht schreiben!", e, false);
        }
    }

}
