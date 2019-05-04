package gasStationSoftware.util;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 )
        );
    }

    public static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    public static String getDateForamtted(Date date) {
        String pattern = "dd.MM.yyyy";
        DateFormat dateFormatter = new SimpleDateFormat(pattern);
        return dateFormatter.format(date);
    }

}
