public enum UserRole {
    USER(1), ADMIN(2), EXIT(0);
    private final int code;
    UserRole(int code) { this.code = code; }
    public int getCode() { return code; }

    public static UserRole fromCode(int code) {
        for (UserRole role : values()) {
            if (role.code == code) return role;
        }
        return null;
    }
}