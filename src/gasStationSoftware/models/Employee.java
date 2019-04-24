package gasStationSoftware.models;

import java.util.Date;

public class Employee {

    private final int EMPLOYEE_NUMBER;
    private final Date EMPLOYMENT_DATE;
    private final String FIRST_NAME, SUR_NAME;

    public Employee(int employeeNumber, Date employmentDate, String firstName, String surname) {
        EMPLOYEE_NUMBER = employeeNumber;
        EMPLOYMENT_DATE = employmentDate;
        FIRST_NAME = firstName;
        SUR_NAME = surname;
    }

    public int getEMPLOYEE_NUMBER() {
        return EMPLOYEE_NUMBER;
    }

    public Date getEMPLOYMENT_DATE() {
        return EMPLOYMENT_DATE;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public String getSUR_NAME() {
        return SUR_NAME;
    }
}
