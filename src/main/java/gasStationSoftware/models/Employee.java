package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;

public class Employee {

    private final int EMPLOYEE_NUMBER;
    private final Date EMPLOYMENT_DATE;
    private final String FIRST_NAME, SUR_NAME, EMPLOYMENT_DATE_FORMATTED;

    public Employee(int employeeNumber, String firstName, String surname, Date employmentDate) {
        EMPLOYEE_NUMBER = employeeNumber;
        EMPLOYMENT_DATE = employmentDate;
        FIRST_NAME = firstName;
        SUR_NAME = surname;
        EMPLOYMENT_DATE_FORMATTED = Utility.getDateFormatted(EMPLOYMENT_DATE);
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

    public String getEMPLOYMENT_DATE_FORMATTED() {
        return EMPLOYMENT_DATE_FORMATTED;
    }
}