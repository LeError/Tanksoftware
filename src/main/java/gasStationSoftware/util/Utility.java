package gasStationSoftware.util;

import gasStationSoftware.models.InventoryType;
import gasStationSoftware.models.ItemType;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utility {

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 )
        );
    }

    public static String Rgb2Hex(Color color) {
        return ("#" + Integer.toHexString(color.getRGB()).substring(2));
    }

    public static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    public static String getDateForamtted(Date date) {
        String pattern = "dd.MM.yyyy";
        DateFormat dateFormatter = new SimpleDateFormat(pattern);
        return dateFormatter.format(date);
    }

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

    public static ArrayList<ItemType> getInventoryType(ItemType[] iTypes, InventoryType type) {
        ArrayList<ItemType> result = new ArrayList<>();
        for(ItemType iType : iTypes){
            if(iType.getTYPE().equals(InventoryType.Fuel.getTYPE()) && type.getTYPE().equals(InventoryType.Fuel.getTYPE())) {
                result.add(iType);
            } else if(iType.getTYPE().equals(InventoryType.Good.getTYPE()) && type.getTYPE().equals(InventoryType.Good.getTYPE())) {
                result.add(iType);
            }
        }
        return result;
    }

}
