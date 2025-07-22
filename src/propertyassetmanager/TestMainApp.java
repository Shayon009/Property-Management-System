package propertyassetmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("=== Testing Main Application Interface ===");
            
            // Test database first
            System.out.println("Setting up database...");
            UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();
            dbHandler.initDatabase();
            dbHandler.setupUsersTable();
            
            // Create a test user
            UserInfo testUser = new UserInfo("admin", "System Administrator", "admin");
            System.out.println("Test user created: " + testUser.fullName);
            
            // Load main interface directly
            System.out.println("Loading main application interface...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            
            // Initialize with test user
            MainController controller = loader.getController();
            controller.initData(testUser);
            
            primaryStage.setTitle("Property Asset Manager - " + testUser.fullName + " (" + testUser.role + ")");
            primaryStage.setScene(new Scene(root, 1200, 800));
            primaryStage.setMaximized(true);
            primaryStage.show();
            
            System.out.println("Main application interface loaded successfully!");
            System.out.println("You should see all tabs: Buildings, Rooms, Assets, Floor Plan, Search & Filter, Backup");
            
        } catch (Exception e) {
            System.err.println("Error loading main application:");
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing the main application interface directly...");
        launch(args);
    }
}
