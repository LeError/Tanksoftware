package gasStationSoftware.util;

import gasStationSoftware.exceptions.DataFileNotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadJSON {

    private JSONParser parser = new JSONParser();
    private File path;
    private JSONObject jsonObject;

    public ReadJSON(String path)
    throws DataFileNotFoundException {
        this.path = new File(path);
        if (this.path.isFile()) {
            read();
        } else {
            throw new DataFileNotFoundException("Error 404 \t Can't find JSON file!");
        }
    }

    public void read() { //TODO add canRead & canWrite
        try (FileReader fileReader = new FileReader(path)) {
            Object obj = parser.parse(fileReader);
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            //TODO
        }
    }

    public String getItemString(String name) {
        return (String) jsonObject.get(name);
    }

    public String[] getItemStringArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (String) list.get(i);
        return items;
    }

    public ArrayList<String>[] getItemStringArrayListArray(String name) {
        JSONArray objectArray = (JSONArray) jsonObject.get(name);
        ArrayList<String>[] items = new ArrayList[objectArray.size()];
        for (int i = 0; i < objectArray.size(); i++) {
            JSONObject jsonObj2 = (JSONObject) objectArray.get(i);
            JSONArray array = (JSONArray) jsonObj2.get("gasPump");
            ArrayList<String> itemList = new ArrayList<>();
            for (int j = 0; j < array.size(); j++) {
                itemList.add((String) array.get(j));
            }
            items[i] = itemList;
        }
        return items;
    }
}