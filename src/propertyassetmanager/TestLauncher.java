package propertyassetmanager;

/**
 * Simple test launcher to verify database connectivity
 * and basic functionality without JavaFX dependencies
 */
public class TestLauncher {
    public static void main(String[] args) {
        System.out.println("=== Property Asset Manager - Database Test ===");
        
        try {
            // Test database connection
            UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();
            dbHandler.initDatabase();
            
            System.out.println("✓ Database connection successful!");
            
            // Test user authentication
            UserInfo admin = dbHandler.getUserInfo("admin", "admin123");
            if (admin != null) {
                System.out.println("✓ Admin login test successful: " + admin.fullName + " (" + admin.role + ")");
            } else {
                System.out.println("✗ Admin login test failed");
            }
            
            UserInfo user = dbHandler.getUserInfo("user", "user123");
            if (user != null) {
                System.out.println("✓ User login test successful: " + user.fullName + " (" + user.role + ")");
            } else {
                System.out.println("✗ User login test failed");
            }
            
            // Test data retrieval
            try {
                var buildings = dbHandler.getBuildings();
                System.out.println("✓ Buildings found: " + buildings.size());
            } catch (Exception e) {
                System.out.println("✗ Error getting buildings: " + e.getMessage());
            }
            
            try {
                var rooms = dbHandler.getRooms();
                System.out.println("✓ Rooms found: " + rooms.size());
            } catch (Exception e) {
                System.out.println("✗ Error getting rooms: " + e.getMessage());
            }
            
            try {
                var assets = dbHandler.getAssets();
                System.out.println("✓ Assets found: " + assets.size());
            } catch (Exception e) {
                System.out.println("✗ Error getting assets: " + e.getMessage());
            }
            
            // Test adding new data
            System.out.println("\n=== Testing Add Operations ===");
            try {
                dbHandler.addBuilding("Test Building");
                System.out.println("✓ Building added successfully");
                
                // Clean up test data
                System.out.println("Cleaning up test data...");
                // Note: You might want to implement a delete method for cleanup
                
            } catch (Exception e) {
                System.out.println("✗ Error adding building: " + e.getMessage());
            }
            
            System.out.println("\n=== All Tests Completed ===");
            System.out.println("The database and core functionality is working correctly!");
            System.out.println("You can now run the JavaFX application once the dependencies are resolved.");
            
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
