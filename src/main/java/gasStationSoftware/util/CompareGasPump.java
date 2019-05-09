package gasStationSoftware.util;

import gasStationSoftware.models.GasPump;

import java.util.Comparator;

public class CompareGasPump implements Comparator<GasPump> {
    public int compare(GasPump gasPumpOne, GasPump gasPumpTwo) {
        return gasPumpOne.getGAS_PUMP_NUMBER() - gasPumpTwo.getGAS_PUMP_NUMBER();
    }
}
