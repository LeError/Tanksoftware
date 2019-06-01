package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;

public class Employee {

    private final int EMPLOYEE_NUMBER;
    private final Date EMPLOYMENT_DATE;
    private final String FIRST_NAME, SUR_NAME, EMPLOYMENT_DATE_FORMATTED;
    private final String PASS;
    private UserRole role;

    /**
     * Constuctor Employee
     * @param employeeNumber
     * @param firstName
     * @param surname
     * @param employmentDate
     * @author Robin Herder
     */
    public Employee(int employeeNumber, String firstName, String surname, Date employmentDate, UserRole role, String pass) {
        EMPLOYEE_NUMBER = employeeNumber;
        EMPLOYMENT_DATE = employmentDate;
        FIRST_NAME = firstName;
        SUR_NAME = surname;
        EMPLOYMENT_DATE_FORMATTED = Utility.getDateFormatted(EMPLOYMENT_DATE);
        PASS = pass;
        this.role = role;
    }

    /**
     * Gibt die Mitarbeiternummer zurück
     * @return EMPLOYEE_NUMBER
     * @author Robin Herder
     */
    public int getEMPLOYEE_NUMBER() {
        return EMPLOYEE_NUMBER;
    }

    /**
     * Gibt das Einstellungsdatum des Mitarbeiters zurück
     * @return EMPLOYMENT_DATE
     * @author Robin Herder
     */
    public Date getEMPLOYMENT_DATE() {
        return EMPLOYMENT_DATE;
    }

    /**
     * Gibt den Vornamen des Mitarbeiters zurück
     * @return FIRST_NAME
     * @author Robin Herder
     */
    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    /**
     * Gibt den Nachnamen des Mitarbeiters zurück
     * @return SUR_NAME
     * @author Robin Herder
     */
    public String getSUR_NAME() {
        return SUR_NAME;
    }

    /**
     * Gibt das Datum als String zurück
     * @return
     * @author Robin Herder
     */
    public String getEMPLOYMENT_DATE_FORMATTED() {
        return EMPLOYMENT_DATE_FORMATTED;
    }

    public boolean logIn(int employeeNumber, String pass) {
        if(EMPLOYEE_NUMBER == employeeNumber && PASS.equals(pass)) {
            return true;
        } else {
            return false;
        }
    }

    public String getRole() {
        return role.getRole();
    }

    public int getIRole() {
        return role.getId();
    }

    public String getPASS() {
        return PASS;
    }
}