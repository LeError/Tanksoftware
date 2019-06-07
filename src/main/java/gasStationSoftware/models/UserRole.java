package gasStationSoftware.models;

public enum UserRole {
    admin(0, "Administrator"),
    employee(1, "Angestellter"),
    assistant(2, "Aushilfe");

    private int id;
    private String role;

    /**
     * Constructor UserRole
     * @param id id der rolle
     * @param role bezeichnung der rolle
     * @author Lea Buchhold
     */
    UserRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    /**
     * Gibt die ID zurück
     * @return
     * @author Lea Buchhold
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt die Rolle zurück
     * @return
     * @author Lea Buchhold
     */
    public String getRole() {
        return role;
    }
}
