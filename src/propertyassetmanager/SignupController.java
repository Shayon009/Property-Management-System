package propertyassetmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Label messageLabel;

    private UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleComboBox.getItems().addAll("user", "admin");
        roleComboBox.setValue("user");
    }

    @FXML
    private void handleSignup() {
        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        // Validation
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showMessage("Please fill in all fields", true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match", true);
            return;
        }

        if (password.length() < 6) {
            showMessage("Password must be at least 6 characters long", true);
            return;
        }

        // Check if username already exists
        if (isUsernameExists(username)) {
            showMessage("Username already exists", true);
            return;
        }

        // Create user
        if (createUser(fullName, username, password, role)) {
            showMessage("Account created successfully!", false);
            // Clear fields
            clearFields();
            // Switch to login after 2 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(this::switchToLogin);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            showMessage("Error creating account", true);
        }
    }

    private boolean isUsernameExists(String username) {
        return dbHandler.usernameExists(username);
    }

    private boolean createUser(String fullName, String username, String password, String role) {
        try {
            dbHandler.createUser(fullName, username, password, role);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    private void clearFields() {
        fullNameField.clear();
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        roleComboBox.setValue("user");
    }

    @FXML
    private void switchToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 