package propertyassetmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();

    @FXML
    private void initialize() {
        try {
            System.out.println("Initializing LoginController...");
            dbHandler.initDatabase();
            dbHandler.setupUsersTable();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("Ready to login");
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Database initialization failed: " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        System.out.println("Login attempt for user: " + username);

        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter both username and password", true);
            return;
        }

        // Authenticate user
        UserInfo userInfo = authenticateUser(username, password);
        if (userInfo == null) {
            showMessage("Invalid username or password", true);
            System.out.println("Authentication failed for user: " + username);
            return;
        }

        System.out.println("Authentication successful for user: " + userInfo.fullName + " (Role: " + userInfo.role + ")");

        try {
            // Load the appropriate interface based on user role
            String fxmlFile;
            String windowTitle;
            
            if ("admin".equals(userInfo.role)) {
                fxmlFile = "Main.fxml";  // Admin gets full access to main interface
                windowTitle = "Property Asset Manager - Admin Panel";
                System.out.println("Loading admin main interface...");
            } else {
                fxmlFile = "UserPanel.fxml";  // User gets limited interface
                windowTitle = "Property Asset Manager - User Panel";
                System.out.println("Loading user panel...");
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            System.out.println(fxmlFile + " loaded successfully");

            // Get the appropriate controller and initialize it with user info
            if ("admin".equals(userInfo.role)) {
                MainController controller = loader.getController();
                System.out.println("Initializing MainController with admin user data");
                controller.initData(userInfo);
            } else {
                UserPanelController controller = loader.getController();
                System.out.println("Initializing UserPanelController with user data");
                controller.initData(userInfo);
            }

            // Switch to the new scene
            System.out.println("Switching to " + userInfo.role + " panel...");
            Stage stage = (Stage) usernameField.getScene().getWindow();
            
            if ("admin".equals(userInfo.role)) {
                stage.setScene(new Scene(root, 1200, 800)); // Larger window for admin
                stage.setMaximized(true); // Start maximized for better view
            } else {
                stage.setScene(new Scene(root, 800, 600)); // Standard window for user
            }
            
            stage.setTitle(windowTitle + " - " + userInfo.fullName);
            System.out.println("Login successful! " + userInfo.role + " interface loaded.");

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error loading application: " + e.getMessage(), true);
            System.err.println("Error loading application: " + e.getMessage());
        }
    }

    private UserInfo authenticateUser(String username, String password) {
        return dbHandler.getUserInfo(username, password);
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    @FXML
    private void switchToSignup() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error loading signup page", true);
        }
    }
} 