package gasStationSoftware.util;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WriteFile {
	public static void writeFile(ArrayList<String> data, String url) {
		try {
			Path file = Paths.get(url);
			Files.write(file, data, Charset.forName("UTF-8"));
		} catch (Exception e) {
			System.err.println("[FileOut]:\t Can't write file. Something went wrong!");
		}
	}
}
