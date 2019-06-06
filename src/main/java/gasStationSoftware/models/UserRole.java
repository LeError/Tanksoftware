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
     * @author Robin Herder
     */
    UserRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    /**
     * Gibt die ID zurück
     * @return
     * @author Robin Herder
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt die Rolle zurück
     * @return
     * @author Robin Herder
     */
    public String getRole() {
        return role;
    }
}
