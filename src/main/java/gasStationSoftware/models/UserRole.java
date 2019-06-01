package gasStationSoftware.models;

public enum UserRole {
    admin(0, "Administrator"),
    employee(1, "Angestellter"),
    assistant(2, "Aushilfe");

    private int id;
    private String role;

    private UserRole(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public boolean hasAcess(int id) {
        if(id >= this.id) {
            return true;
        } else {
            return false;
        }
    }
}
