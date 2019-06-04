package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class CustomerOrder
extends Document {

    private HashMap<Fuel, Float> fuels = new HashMap<>();
    private HashMap<Good, Integer> goods = new HashMap<>();

    private final int RECEIPT_NUMBER;
    private final Employee EMPLOYEE;

    /**
     * Constructor CustomerOrder
     * @param receiptNumber
     * @param date
     * @param employee
     * @param fuels
     * @param goods
     * @author Robin Herder
     */
    public CustomerOrder(int receiptNumber, Date date, Employee employee, ArrayList<Fuel> fuels,
    ArrayList<Good> goods) {
        super(DocumentType.receipt, "RECEIPT_" + receiptNumber, date);
        RECEIPT_NUMBER = receiptNumber;
        EMPLOYEE = employee;
        for(Fuel fuel : fuels) {
            this.fuels.put(fuel, fuel.getCheckoutAmount());
            fuel.setCheckoutAmount(0);
        }
        for(Good good : goods) {
            this.goods.put(good, (int) good.getCheckoutAmount());
            good.setCheckoutAmount(0);
        }
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    public String[] getLinesForFile() {
        return null;
    }

    /**
     * Gibt Gesamtpreis zurück
     * @return total
     * @author Robin Herder
     */
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
            total += good.getPrice() * goods.get(good);
        }
        return Utility.round(total, 2);
    }

    /**
     * Gibt Auftragsdatum zurück
     * @return date
     * @author Robin Herder
     */
    public String getDate(){
        return Utility.getDateFormatted(getODATE());
    }

    /**
     *
     * @return rFuels[]
     * @author Robin Herder
     */
    public ArrayList<Fuel> getFuels() {
        ArrayList<Fuel> rFuels = new ArrayList<>();
        Iterator<Fuel> fuelKey = fuels.keySet().iterator();
        while (fuelKey.hasNext()) {
            rFuels.add(fuelKey.next());
        }
        return rFuels;
    }

    /**
     * Gibt die Kraftstoffmenge zurück
     * @return amount[]
     * @author Robin Herder
     */
    public ArrayList<Float> getFuelsAmount() {
        ArrayList<Float> amount = new ArrayList<>();
        Iterator<Fuel> fuelKey = fuels.keySet().iterator();
        while (fuelKey.hasNext()) {
            amount.add(fuels.get(fuelKey.next()));
        }
        return amount;
    }

    /**
     * Gibt die Warenmenge zurück
     * @return amount[]
     * @author Robin Herder
     */
    public ArrayList<Integer> getGoodsAmount() {
        ArrayList<Integer> amount = new ArrayList<>();
        Iterator<Good> goodKey = goods.keySet().iterator();
        while (goodKey.hasNext()) {
            amount.add(goods.get(goodKey.next()));
        }
        return amount;
    }

    /**
     * Gibt die Menge zurück
     * @return rGoods[]
     * @author Robin Herder
     */
    public ArrayList<Good> getGoods() {
        ArrayList<Good> rGoods = new ArrayList<>();
        Iterator<Good> goodKey = goods.keySet().iterator();
        while (goodKey.hasNext()) {
            rGoods.add(goodKey.next());
        }
        return rGoods;
    }

    /**
     * Gibt die Quittungsnummer zurück
     * @return recipt_number
     * @author Robin Herder
     */
    public int getRECEIPT_NUMBER() {
        return RECEIPT_NUMBER;
    }

    /**
     * Gibt den Mitarbeiter zurück
     * @return employee
     * @author Robin Herder
     */
    public Employee getEMPLOYEE() {
        return EMPLOYEE;
    }

    /**
     *
     * @return
     * @author Robin Herder
     */
    @Override public String getTotalForTab() {
        return "+ " + getTotal();
    }
}
