package gasStationSoftware.util;

import gasStationSoftware.exceptions.dataFileNotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class ReadJSON {

    private JSONParser parser = new JSONParser();
    private File path;
    private JSONObject jsonObject;

    public ReadJSON(String path) throws dataFileNotFoundException {
        this.path = new File(path);
        if (this.path.exists()) {
            read();
        } else {
            throw new dataFileNotFoundException();
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

    public byte getItemByte(String name) {
        return (byte) jsonObject.get(name);
    }

    public char getItemChar(String name) {
        return (char) jsonObject.get(name);
    }

    public String getItemString(String name) {
        return (String) jsonObject.get(name);
    }

    public short getItemShort(String name) {
        return (short) jsonObject.get(name);
    }

    public int getItemInt(String name) {
        return (int) jsonObject.get(name);
    }

    public double getItemDouble(String name) {
        return (double) jsonObject.get(name);
    }

    public float getItemFloat(String name) {
        return (float) jsonObject.get(name);
    }

    public long getItemLong(String name) {
        return (long) jsonObject.get(name);
    }

    public boolean getItemBoolean(String name) {
        return (boolean) jsonObject.get(name);
    }

    public byte[] getItemByteArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        byte[] items = new byte[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (byte) list.get(i);
        return items;
    }

    public char[] getItemCharArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        char[] items = new char[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (char) list.get(i);
        return items;
    }

    public String[] getItemStringArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        String[] items = new String[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (String) list.get(i);
        return items;
    }

    public short[] getItemShortArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        short[] items = new short[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (short) list.get(i);
        return items;
    }

    public int[] getItemIntArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        int[] items = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = Integer.parseInt((String) list.get(i));
        return items;
    }

    public double[] getItemDoubleArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        double[] items = new double[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (double) list.get(i);
        return items;
    }

    public float[] getItemFloatArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        float[] items = new float[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (float) list.get(i);
        return items;
    }

    public long[] getItemLongArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        long[] items = new long[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (long) list.get(i);
        return items;
    }

    public boolean[] getItemBooleanArray(String name) {
        JSONArray list = (JSONArray) jsonObject.get(name);
        boolean[] items = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++)
            items[i] = (boolean) list.get(i);
        return items;
    }
}
