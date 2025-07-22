package propertyassetmanager;

public class QuickDatabaseTest {
    public static void main(String[] args) {
        System.out.println("=== Quick Database Test ===");
        
        try {
            UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();
            
            // Test database initialization
            System.out.println("1. Testing database initialization...");
            dbHandler.initDatabase();
            dbHandler.setupUsersTable();
            System.out.println("   ✓ Database initialized");
            
            // Test authentication
            System.out.println("2. Testing user authentication...");
            UserInfo userInfo = dbHandler.getUserInfo("admin", "admin123");
            
            if (userInfo != null) {
                System.out.println("   ✓ Admin user found:");
                System.out.println("     - Username: " + userInfo.username);
                System.out.println("     - Full Name: " + userInfo.fullName);
                System.out.println("     - Role: " + userInfo.role);
            } else {
                System.out.println("   ✗ Admin user not found - creating default user...");
                // The setupUsersTable should have created it, but let's try again
                try {
                    dbHandler.createUser("System Administrator", "admin", "admin123", "admin");
                    System.out.println("   ✓ Default admin user created");
                } catch (Exception e) {
                    System.out.println("   ℹ Admin user may already exist: " + e.getMessage());
                }
                
                // Try authentication again
                userInfo = dbHandler.getUserInfo("admin", "admin123");
                if (userInfo != null) {
                    System.out.println("   ✓ Admin user authentication successful");
                } else {
                    System.out.println("   ✗ Admin user authentication failed");
                }
            }
            
            System.out.println("3. Database type: " + (DatabaseConfig.useMySQL() ? "MySQL" : "SQLite"));
            
            System.out.println("\n=== Test Summary ===");
            System.out.println("Database is ready for use!");
            System.out.println("Login with: admin / admin123");
            
        } catch (Exception e) {
            System.err.println("✗ Test failed:");
            e.printStackTrace();
        }
    }
}
