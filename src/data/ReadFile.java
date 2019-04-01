package data;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

	private final File FILE;
	private final ArrayList<String> LINES = new ArrayList<>();

	public ReadFile(String url) {
		FILE = new File(url);
		try {
			Scanner scan = new Scanner(FILE);
			while(scan.hasNextLine()) {
				LINES.add(scan.nextLine());
			}
			scan.close();
		} catch(Exception e) {
			System.err.println("[FileIn]:\nCan't read File. Something went wrong!\n" + e.getMessage());
		}
	}

	public ArrayList<String> getLINES(){
		return LINES;
	}

}