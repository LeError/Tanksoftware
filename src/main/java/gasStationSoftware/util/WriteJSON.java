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
     * Constructor for a WriteJSON Objekt
     * @param path Pfad wo die datei erstellt wird
     * @author Robin Herder
     */
    public WriteJSON(String path) {
        this.path = new File(path);
        if (!this.path.getParentFile().isDirectory())
            this.path.getParentFile().mkdir();
    }

    /**
     * Fügt ein Variable hinzu
     * @param name bezeichner der var
     * @param item inhalt der var
     * @author Robin Herder
     */
    public void addItem(String name, String item) {
        obj.put(name, item);
    }

    /**
     * Fügt ein array hinzu
     * @param name bezeichner der var
     * @param item zu schreibendes array
     * @author Robin Herder
     */
    public void addItemArray(String name, String[] items) {
        JSONArray list = new JSONArray();
        for (String item : items)
            list.add(item);
        obj.put(name, list);
    }

    /**
     * Schreibt ein Arraylisten Array in json
     * @param name name des arrays
     * @param subName name der arraylisten
     * @param items ArrayListArray
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
     * @param overwrite Ob falls die atei schon existiert überschrieben werden soll
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