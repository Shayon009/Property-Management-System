package propertyassetmanager;

public class UserInfo {
    public String username;
    public String fullName;
    public String role;
    
    public UserInfo() {
        // Default constructor
    }
    
    public UserInfo(String username, String fullName, String role) {
        this.username = username;
        this.fullName = fullName;
        this.role = role;
    }
    
    @Override
    public String toString() {
        return fullName + " (" + role + ")";
    }
}
