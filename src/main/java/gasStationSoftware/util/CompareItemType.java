package gasStationSoftware.util;

import gasStationSoftware.models.*;

import java.util.Comparator;

public class CompareItemType implements Comparator<ItemType> { //TODO refactor move
    public int compare(ItemType iTypeOne, ItemType iTypeTwo) {
        return iTypeOne.getINVENTORY_NUMBER() - iTypeTwo.getINVENTORY_NUMBER();
    }
}