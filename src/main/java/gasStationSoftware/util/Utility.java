package gasStationSoftware.util;

import gasStationSoftware.models.Document;
import gasStationSoftware.models.DocumentType;
import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;

import java.awt.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utility {

    /**
     * Gibt eine Farbe des eingegebenen Hex-Codes zurück
     * @param colorStr
     * @return Color
     * @author Robin Herder
     */
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 )
        );
    }

    /**
     * Gibt den Hex-Code der eingegebenen Farbe zurück
     * @param color
     * @return String
     * @author Robin Herder
     */
    public static String Rgb2Hex(Color color) {
        return ("#" + Integer.toHexString(color.getRGB()).substring(2));
    }

    /**
     * Gibt das Datum als String zurück
     * @return String
     * @author Robin Herder
     */
    public static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    /**
     * Gibt das eingegebene Datum als String zurück
     * @param date
     * @return String
     * @author Robin Herder
     */
    public static String getDateFormatted(Date date) {
        String pattern = "dd.MM.yyyy";
        DateFormat dateFormatter = new SimpleDateFormat(pattern);
        return dateFormatter.format(date);
    }

    /**
     *
     * @param array
     * @return IntArray
     * @author Robin Herder
     */
    public static int[] getIntArray(String[] array) {
        int[] reArray = new int[array.length];
        try {
            for(int i = 0; i < array.length; i++) {
                reArray[i] = Integer.parseInt(array[i]);
            }
        } catch(Exception e) {
            reArray = null;
        }
        return reArray;
    }

    /**
     *
     * @param array
     * @return FloatArray
     * @author Robin Herder
     */
    public static float[] getFloatArray(String[] array) {
        float[] reArray = new float[array.length];
        try {
            for(int i = 0; i < array.length; i++) {
                reArray[i] = Float.parseFloat(array[i]);
            }
        } catch(Exception e) {
            reArray = null;
        }
        return reArray;
    }

    /**
     * Gibt den InventoryType zurück
     * @param iTypes
     * @param type
     * @return InventoryType
     * @author Robin Herder
     */
    public static ArrayList<ItemType> getInventoryType(ArrayList<ItemType> iTypes, InventoryType type) {
        ArrayList<ItemType> result = new ArrayList<>();
        for(ItemType iType : iTypes){
            if(iType.getTYPE() == type) {
                result.add(iType);
            }
        }
        return result;
    }

    /**
     *
     * @param documents
     * @param docType
     * @return result[]
     * @author Robin Herder
     */
    public static ArrayList<?> getDocument(ArrayList<Document> documents, DocumentType docType) {
        ArrayList<Document> result = new ArrayList<>();
        for(Document document : documents) {
            if(document.getDOC_TYPE() == docType) {
                result.add(document);
            }
        }
        return result;
    }

    /**
     * Rundet die eingebene Zahl
     * @param number
     * @param decimals
     * @return float
     * @author Robin Herder
     */
    public static float round(float number, int decimals) {
        BigDecimal bd = new BigDecimal(Float.toString(number));
        bd = bd.setScale(decimals, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
