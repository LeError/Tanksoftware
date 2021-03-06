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

    public static javafx.scene.paint.Color getFXColor(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlpha();
        double opacity = a / 255.0;
        return javafx.scene.paint.Color.rgb(r, g, b, opacity);
    }

    /**
     * Gibt ein Farbobjekt des eingegebenen Hex-Codes zurück
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
     * Gibt das aktuelle Datum als String zurück
     * @return String
     * @author Robin Herder
     */
    public static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    /**
     * Gibt das eingegebene Datum als String zurück
     * @param date zu convetierendes datum
     * @return String
     * @author Robin Herder
     */
    public static String getDateFormatted(Date date) {
        String pattern = "dd.MM.yyyy";
        DateFormat dateFormatter = new SimpleDateFormat(pattern);
        return dateFormatter.format(date);
    }

    /**
     * String Array zu Integer Array
     * @param array zu parsender Array
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
     * String Array zu Float Array
     * @param array zu parsender Array
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
     * Gibt eine Arraylist mit allen elementen der Ursprünglichen ArrayList mit dem entsprechenden InventoryType
     * @param iTypes Liste der ItemTypen
     * @param type IventoryType zur selektierung
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
     * Gibt ArrayList mit Document des Ausgewählten typs zurück
     * @param documents Liste der Documente
     * @param docType Documenttyp der selektiert werden soll
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
     * @param number Zu rundende Zahl
     * @param decimals nachkommastellen
     * @return float
     * @author Robin Herder
     */
    public static float round(float number, int decimals) {
        BigDecimal bd = new BigDecimal(Float.toString(number));
        bd = bd.setScale(decimals, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
