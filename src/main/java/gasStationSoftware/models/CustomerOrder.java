package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CustomerOrder {

    private HashMap<Fuel, Float> fuels = new HashMap<>();
    private HashMap<Good, Integer> goods = new HashMap<>();

    private final String TITLE;
    private final int RECEIPT_NUMBER;
    private final Date DATE;
    private final Employee EMPLOYEE;

    public CustomerOrder(String title, int receiptNumber, Date date, Employee employee) {
        TITLE = title;
        RECEIPT_NUMBER = receiptNumber;
        DATE = date;
        EMPLOYEE = employee;
    }

    public void addFuel(Fuel fuel, float amount) {
        fuels.put(fuel, amount);
    }

    public void addGood(Good good, int amount) {
        goods.put(good, amount);
    }

    public String[] getLinesForFile() {
        return null;
    }

    public float getTotal() {
        Iterator<Fuel> fuelKey = fuels.keySet().iterator();
        Iterator<Good> goodKey = goods.keySet().iterator();
        float total = 0;
        while(fuelKey.hasNext()) {
            Fuel fuel = fuelKey.next();
            total +=  fuel.getPrice() * fuels.get(fuel);
        }
        while(goodKey.hasNext()) {
            Good good = goodKey.next();
            total +=  good.getPrice() * fuels.get(good);
        }
        return total;
    }

    public String getDate(){
        return Utility.getDateFormatted(DATE);
    }

}
