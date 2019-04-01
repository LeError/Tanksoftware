package data;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class WriteFile {
	public static void writeFile(String[] data, String url) {
		try {
			ArrayList<String> lines = new ArrayList<>();
			for (int i = 0; i < data.length; i++) {
				lines.add(data[i]);
			}
			Path file = Paths.get(url);
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (Exception e) {
			System.err.println("[FileOut]:\t Can't write file. Something went wrong!");
		}
	}

	public static void writeFile(String data, String url) {
		try {
			ArrayList<String> lines = new ArrayList<>();
			lines.add(data);
			Path file = Paths.get(url);
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (Exception e) {
			System.err.println("[FileOut]:\t Can't write file. Something went wrong!");
		}
	}

	public static void writeFile(ArrayList<String> data, String url) {
		try {
			Path file = Paths.get(url);
			Files.write(file, data, Charset.forName("UTF-8"));
		} catch (Exception e) {
			System.err.println("[FileOut]:\t Can't write file. Something went wrong!");
		}
	}
}
