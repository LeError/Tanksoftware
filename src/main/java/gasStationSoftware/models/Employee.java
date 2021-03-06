package gasStationSoftware.models;

import gasStationSoftware.util.Utility;

import java.util.Date;

public class Employee {

    private final int EMPLOYEE_NUMBER;
    private final Date EMPLOYMENT_DATE;
    private final String EMPLOYMENT_DATE_FORMATTED;
    private String pass, firstName, surName;
    private UserRole role;

    /**
     * Constuctor Employee
     * @param employeeNumber Persnummer des angestelten
     * @param firstName vorname des angestelten
     * @param surname nachname des angestelten
     * @param employmentDate einstellungsdatum des angestellten
     * @author Lea Buchhold
     */
    public Employee(int employeeNumber, String firstName, String surname, Date employmentDate, UserRole role, String pass) {
        EMPLOYEE_NUMBER = employeeNumber;
        EMPLOYMENT_DATE = employmentDate;
        this.firstName = firstName;
        surName = surname;
        EMPLOYMENT_DATE_FORMATTED = Utility.getDateFormatted(EMPLOYMENT_DATE);
        this.pass = pass;
        this.role = role;
    }

    /**
     * Gibt die Mitarbeiternummer zurück
     * @return EMPLOYEE_NUMBER
     * @author Lea Buchhold
     */
    public int getEMPLOYEE_NUMBER() {
        return EMPLOYEE_NUMBER;
    }

    /**
     * Gibt das Einstellungsdatum des Mitarbeiters zurück
     * @return EMPLOYMENT_DATE
     * @author Lea Buchhold
     */
    public Date getEMPLOYMENT_DATE() {
        return EMPLOYMENT_DATE;
    }

    /**
     * Gibt den Vornamen des Mitarbeiters zurück
     * @return firstName
     * @author Lea Buchhold
     */
    public String getFIRST_NAME() {
        return firstName;
    }

    /**
     * Gibt den Nachnamen des Mitarbeiters zurück
     * @return surName
     * @author Lea Buchhold
     */
    public String getSUR_NAME() {
        return surName;
    }

    /**
     * Gibt das Datum als String zurück
     * @return datum
     * @author Lea Buchhold
     */
    public String getEMPLOYMENT_DATE_FORMATTED() {
        return EMPLOYMENT_DATE_FORMATTED;
    }

    /**
     * Prüft ob der Mitarbeiter der einloggende Mitarbeiter ist
     * @param employeeNumber Persnummer
     * @param pass Angestellten password
     * @return boolean
     * @author Robin Herder
     */
    public boolean logIn(int employeeNumber, String pass) {
        if(EMPLOYEE_NUMBER == employeeNumber && this.pass.equals(pass)) {
            return true;
        }
        return false;
    }

    /**
     * Gibt die Rolle des Mitarbeiters zurück
     * @return role
     * @author Lea Buchhold
     */
    public String getRole() {
        return role.getRole();
    }

    /**
     * Gibt die Rollen-ID zurück
     * @return roleID
     * @author Lea Buchhold
     */
    public int getIRole() {
        return role.getId();
    }

    /**
     * Gibt den Pass zurück
     * @return pass
     * @author Lea Buchhold
     */
    public String getPASS() {
        return pass;
    }

    /**
     * Den Pass einstellen
     * @param pass
     * @author Lea Buchhold
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Den Vornamen festlegen
     * @param firstName
     * @author Lea Buchhold
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Den Nachnamen festlegen
     * @param surName
     * @author Lea Buchhold
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }

    /**
     * Die Mitarbeiterrolle festlegen
     * @param role
     * @author Lea Buchhold
     */
    public void setRole(UserRole role) {
        this.role = role;
    }
}