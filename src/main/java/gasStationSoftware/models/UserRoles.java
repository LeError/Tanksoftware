package gasStationSoftware.models;

public enum UserRoles {
    ;

    private int id;
    private String role;

    private UserRoles(int id, String role) {
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
