package simpleorganizer.model;

public enum UserGroupType {
    ADMIN("admin"), USER("user"), GUEST("guest");

    private String role;

    private UserGroupType(String role) {
        this.setRole(role);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
