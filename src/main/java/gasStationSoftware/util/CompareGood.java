package gasStationSoftware.util;

import gasStationSoftware.models.Good;

import java.util.Comparator;

public class CompareGood implements Comparator<Good> {
    public int compare(Good goodOne, Good goodTwo) {
        return goodOne.getINVENTORY_NUMBER() - goodTwo.getINVENTORY_NUMBER();
    }
}
