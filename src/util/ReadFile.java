package util;

import exceptions.OSException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

	private final File FILE;
	private final ArrayList<String> LINES = new ArrayList<>();
	private final ArrayList<String> TOP_LINES = new ArrayList<>();

	public ReadFile(String url)
	throws OSException {
		if (!isWindows()) {
			throw new OSException("At the moment only Windows is supported");
		}
		FILE = new File(url);
		try {
			Scanner scan = new Scanner(FILE);
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.contains(";")) {
					LINES.add(line);
				} else {
					TOP_LINES.add(line);
				}
			}
			scan.close();
		} catch(Exception e) {
			System.err.println("[FileIn]:\nCan't read File. Something went wrong!\n" + e.getMessage());
		}
	}

	public String[][] getLINES() {
		String[][] lines = new String[LINES.size()][];
		for (int i = 0; i < LINES.size(); i++) {
			lines[i] = getLine(LINES.get(i));
		}
		return lines;
	}

	public ArrayList<String> getTOP_LINES() {
		return TOP_LINES;
	}

	private String[] getLine(String line) {
		return line.trim().split(";");
	}

	private boolean isWindows() {
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			return true;
		} else {
			return false;
		}
	}
}