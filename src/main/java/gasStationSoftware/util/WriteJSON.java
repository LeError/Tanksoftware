package gasStationSoftware.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteJSON {

    private File path;
    private JSONObject obj = new JSONObject();

    /**
     * Constructor WriteJSON
     * @param path
     * @author Robin Herder
     */
    public WriteJSON(String path) {
        this.path = new File(path);
        if (!this.path.getParentFile().isDirectory())
            this.path.getParentFile().mkdir();
    }

    /**
     * Fügt ein Item hinzu
     * @param name
     * @param item
     * @author Robin Herder
     */
    public void addItem(String name, String item) {
        obj.put(name, item);
    }

    /**
     *
     * @param name
     * @param items
     * @author Robin Herder
     */
    public void addItemArray(String name, String[] items) {
        JSONArray list = new JSONArray();
        for (String item : items)
            list.add(item);
        obj.put(name, list);
    }

    /**
     *
     * @param name
     * @param items
     * @author Robin Herder
     */
    public void addItemArrayList(String name, ArrayList<String> items) {
        JSONArray list = new JSONArray();
        for (String item : items)
            list.add(item);
        obj.put(name, list);
    }

    /**
     *
     * @param name
     * @param subName
     * @param items
     * @author Robin Herder
     */
    public void addItemArrayListArray(String name, String subName, ArrayList<String>[] items) {
        JSONArray list = new JSONArray();
        for (ArrayList<String> entry : items) {
            JSONObject subObj = new JSONObject();
            JSONArray subList = new JSONArray();
            for (String item : entry) {
                subList.add(item);
            }
            subObj.put(subName, subList);
            list.add(subObj);
        }
        obj.put(name, list);
    }

    /**
     * Schreibt die JSON
     * @param overwrite
     * @author Robin Herder
     */
    public void write(boolean overwrite) {
        if (overwrite && path.isFile() || !path.isFile()) {
            try (FileWriter file = new FileWriter(path)) {
                System.out.println("[JSONWriter][write][" + path.getName() + "]" + obj.toJSONString());
                file.write(obj.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}