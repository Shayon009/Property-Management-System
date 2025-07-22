package propertyassetmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("Starting TestMain application...");
            
            // Test database first
            System.out.println("Testing database connection...");
            UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();
            dbHandler.initDatabase();
            dbHandler.setupUsersTable();
            System.out.println("Database test successful!");
            
            // Load FXML
            System.out.println("Loading Login.fxml...");
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            System.out.println("FXML loaded successfully!");
            
            primaryStage.setTitle("Property Asset Manager - Test Mode");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
            
            System.out.println("Application started successfully!");
            System.out.println("Use credentials: admin / admin123");
            
        } catch (Exception e) {
            System.err.println("Error starting application:");
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Property Asset Manager Test ===");
        launch(args);
    }
}
