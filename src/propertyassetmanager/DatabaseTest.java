package propertyassetmanager;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("=== Property Asset Manager Database Test ===");
        
        // Test current configuration
        System.out.println("Current Database: " + (DatabaseConfig.useMySQL() ? "MySQL" : "SQLite"));
        
        try {
            UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();
            
            // Test database initialization
            System.out.println("Initializing database...");
            dbHandler.initDatabase();
            dbHandler.setupUsersTable();
            System.out.println("✓ Database initialized successfully");
            
            // Test user authentication
            System.out.println("Testing user authentication...");
            UserInfo user = dbHandler.getUserInfo("admin", "admin123");
            if (user != null) {
                System.out.println("✓ Admin user found: " + user.fullName + " (" + user.role + ")");
            } else {
                System.out.println("✗ Admin user not found");
            }
            
            // Test building operations
            System.out.println("Testing building operations...");
            int initialBuildingCount = dbHandler.getBuildings().size();
            System.out.println("Initial building count: " + initialBuildingCount);
            
            dbHandler.addBuilding("Test Building");
            int newBuildingCount = dbHandler.getBuildings().size();
            System.out.println("Building count after adding: " + newBuildingCount);
            
            if (newBuildingCount > initialBuildingCount) {
                System.out.println("✓ Building operations working");
            } else {
                System.out.println("✗ Building operations failed");
            }
            
            System.out.println("=== Test completed successfully ===");
            
        } catch (Exception e) {
            System.err.println("✗ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
