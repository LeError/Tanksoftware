package gasStationSoftware.util;

import gasStationSoftware.models.Employee;

import java.util.Comparator;

public class CompareEmployee implements Comparator<Employee> {
    public int compare(Employee employeeOne, Employee employeeTwo) {
        return employeeOne.getEMPLOYEE_NUMBER() - employeeTwo.getEMPLOYEE_NUMBER();
    }
}
