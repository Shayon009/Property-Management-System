package propertyassetmanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DatabaseSwitcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Database Configuration");
        
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        
        Label titleLabel = new Label("Database Configuration");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Database type selection
        Label dbTypeLabel = new Label("Database Type:");
        ToggleGroup dbTypeGroup = new ToggleGroup();
        RadioButton sqliteRadio = new RadioButton("SQLite (Local)");
        RadioButton mysqlRadio = new RadioButton("MySQL (XAMPP)");
        sqliteRadio.setToggleGroup(dbTypeGroup);
        mysqlRadio.setToggleGroup(dbTypeGroup);
        
        // Set current selection
        if (DatabaseConfig.useMySQL()) {
            mysqlRadio.setSelected(true);
        } else {
            sqliteRadio.setSelected(true);
        }
        
        // MySQL configuration fields
        GridPane mysqlConfig = new GridPane();
        mysqlConfig.setHgap(10);
        mysqlConfig.setVgap(10);
        mysqlConfig.setPadding(new Insets(10));
        
        TextField hostField = new TextField(DatabaseConfig.getMySQLHost());
        TextField portField = new TextField(DatabaseConfig.getMySQLPort());
        TextField databaseField = new TextField(DatabaseConfig.getMySQLDatabase());
        TextField usernameField = new TextField(DatabaseConfig.getMySQLUsername());
        PasswordField passwordField = new PasswordField();
        passwordField.setText(DatabaseConfig.getMySQLPassword());
        
        mysqlConfig.add(new Label("Host:"), 0, 0);
        mysqlConfig.add(hostField, 1, 0);
        mysqlConfig.add(new Label("Port:"), 0, 1);
        mysqlConfig.add(portField, 1, 1);
        mysqlConfig.add(new Label("Database:"), 0, 2);
        mysqlConfig.add(databaseField, 1, 2);
        mysqlConfig.add(new Label("Username:"), 0, 3);
        mysqlConfig.add(usernameField, 1, 3);
        mysqlConfig.add(new Label("Password:"), 0, 4);
        mysqlConfig.add(passwordField, 1, 4);
        
        // Enable/disable MySQL config based on selection
        mysqlConfig.setDisable(!DatabaseConfig.useMySQL());
        
        mysqlRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            mysqlConfig.setDisable(!newVal);
        });
        
        // Test connection button
        Button testButton = new Button("Test Connection");
        Button saveButton = new Button("Save Configuration");
        Button cancelButton = new Button("Cancel");
        
        Label statusLabel = new Label();
        
        testButton.setOnAction(e -> {
            try {
                if (mysqlRadio.isSelected()) {
                    // Test MySQL connection
                    DatabaseConfig.setMySQLConfig(
                        hostField.getText(),
                        portField.getText(),
                        databaseField.getText(),
                        usernameField.getText(),
                        passwordField.getText()
                    );
                    
                    MySQLDatabaseHandler testHandler = new MySQLDatabaseHandler();
                    testHandler.connect().close();
                    statusLabel.setText("✓ Connection successful!");
                    statusLabel.setStyle("-fx-text-fill: green;");
                } else {
                    // Test SQLite connection
                    DatabaseHandler testHandler = new DatabaseHandler();
                    testHandler.connect().close();
                    statusLabel.setText("✓ SQLite connection successful!");
                    statusLabel.setStyle("-fx-text-fill: green;");
                }
            } catch (Exception ex) {
                statusLabel.setText("✗ Connection failed: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });
        
        saveButton.setOnAction(e -> {
            try {
                if (mysqlRadio.isSelected()) {
                    DatabaseConfig.setMySQLConfig(
                        hostField.getText(),
                        portField.getText(),
                        databaseField.getText(),
                        usernameField.getText(),
                        passwordField.getText()
                    );
                    DatabaseConfig.setUseMySQL(true);
                } else {
                    DatabaseConfig.setUseMySQL(false);
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Configuration Saved");
                alert.setHeaderText(null);
                alert.setContentText("Database configuration has been saved successfully!");
                alert.showAndWait();
                
                primaryStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to save configuration: " + ex.getMessage());
                alert.showAndWait();
            }
        });
        
        cancelButton.setOnAction(e -> primaryStage.close());
        
        // Layout
        VBox dbTypeBox = new VBox(5);
        dbTypeBox.getChildren().addAll(dbTypeLabel, sqliteRadio, mysqlRadio);
        
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(testButton, saveButton, cancelButton);
        
        root.getChildren().addAll(
            titleLabel,
            new Separator(),
            dbTypeBox,
            new Separator(),
            new Label("MySQL Configuration:"),
            mysqlConfig,
            new Separator(),
            buttonBox,
            statusLabel
        );
        
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
