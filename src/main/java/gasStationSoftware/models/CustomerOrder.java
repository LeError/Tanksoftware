package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CustomerOrder {

    private HashMap<Fuel, Float> fuels = new HashMap<>();
    private HashMap<Good, Integer> goods = new HashMap<>();

    private final int RECEIPT_NUMBER;
    private final Date DATE;
    private final Employee EMPLOYEE;

    public CustomerOrder(int receiptNumber, Date date, Employee employee, ArrayList<Fuel> fuels,
    ArrayList<Good> goods) {
        RECEIPT_NUMBER = receiptNumber;
        DATE = date;
        EMPLOYEE = employee;
        for(Fuel fuel : fuels) {
            this.fuels.put(fuel, fuel.getCheckoutAmount());
        }
        for(Good good : goods) {
            this.goods.put(good, (int) good.getCheckoutAmount());
        }
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
        return Utility.round(total, 2);
    }

    public String getDate(){
        return Utility.getDateFormatted(DATE);
    }

    public ArrayList<Fuel> getFuels() {
        ArrayList<Fuel> rFuels = new ArrayList<>();
        Iterator<Fuel> fuelKey = fuels.keySet().iterator();
        while (fuelKey.hasNext()) {
            rFuels.add(fuelKey.next());
        }
        return rFuels;
    }

    public ArrayList<Float> getFuelsAmount() {
        ArrayList<Float> amount = new ArrayList<>();
        Iterator<Fuel> fuelKey = fuels.keySet().iterator();
        while (fuelKey.hasNext()) {
            amount.add(fuels.get(fuelKey.next()));
        }
        return amount;
    }

    public ArrayList<Integer> getGoodsAmount() {
        ArrayList<Integer> amount = new ArrayList<>();
        Iterator<Good> goodKey = goods.keySet().iterator();
        while (goodKey.hasNext()) {
            amount.add(goods.get(goodKey.next()));
        }
        return amount;
    }

    public ArrayList<Good> getGoods() {
        ArrayList<Good> rGoods = new ArrayList<>();
        Iterator<Good> goodKey = goods.keySet().iterator();
        while (goodKey.hasNext()) {
            rGoods.add(goodKey.next());
        }
        return rGoods;
    }

    public int getRECEIPT_NUMBER() {
        return RECEIPT_NUMBER;
    }

    public Employee getEMPLOYEE() {
        return EMPLOYEE;
    }
}
