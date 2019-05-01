package gasStationSoftware.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WriteFile {

    private final Path FILE;
    private ArrayList<String> lines = new ArrayList<>();

    public WriteFile(String path){
        FILE = Paths.get(path);
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void write() throws IOException {
        Files.write(FILE, lines, Charset.forName("UTF-8"));
    }

}
