package gasStationSoftware.util;

import gasStationSoftware.models.ItemType;

import java.util.Comparator;

public class CompareItemType implements Comparator<ItemType> {
    @Override
    public int compare(ItemType iTypeOne, ItemType iTypeTwo) {
        return iTypeOne.getINVENTORY_NUMBER() - iTypeTwo.getINVENTORY_NUMBER();
    }
}
