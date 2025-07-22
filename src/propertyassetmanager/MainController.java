package propertyassetmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    @FXML private TextField buildingNameField;
    @FXML private TableView<Building> buildingTable;
    @FXML private TableColumn<Building, Number> buildingIdCol;
    @FXML private TableColumn<Building, String> buildingNameCol;

    @FXML private TextField roomNameField;
    @FXML private ComboBox<String> roomTypeCombo;
    @FXML private ComboBox<Building> roomBuildingCombo;
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, Number> roomIdCol;
    @FXML private TableColumn<Room, String> roomNameCol;
    @FXML private TableColumn<Room, String> roomTypeCol;
    @FXML private TableColumn<Room, String> roomBuildingCol;
    @FXML private TableColumn<Room, Boolean> roomAvailabilityCol;

    @FXML private TextField assetNameField;
    @FXML private ComboBox<String> assetTypeCombo;
    @FXML private ComboBox<Room> assetRoomCombo;
    @FXML private ComboBox<String> assetConditionCombo;
    @FXML private TableView<Asset> assetTable;
    @FXML private TableColumn<Asset, Number> assetIdCol;
    @FXML private TableColumn<Asset, String> assetNameCol;
    @FXML private TableColumn<Asset, String> assetTypeCol;
    @FXML private TableColumn<Asset, String> assetRoomCol;
    @FXML private TableColumn<Asset, String> assetConditionCol;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterTypeCombo;
    @FXML private TableView<Object> searchTable;
    @FXML private TableColumn<Object, Number> searchIdCol;
    @FXML private TableColumn<Object, String> searchNameCol;
    @FXML private TableColumn<Object, String> searchTypeCol;
    @FXML private TableColumn<Object, String> searchDetailsCol;

    @FXML private Button openFloorPlanBtn;

    private UnifiedDatabaseHandler dbHandler;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button addBuildingBtn;
    @FXML
    private Button editBuildingBtn;
    @FXML
    private Button deleteBuildingBtn;
    @FXML
    private Button addRoomBtn;
    @FXML
    private Button editRoomBtn;
    @FXML
    private Button deleteRoomBtn;
    @FXML
    private Button addAssetBtn;
    @FXML
    private Button editAssetBtn;
    @FXML
    private Button deleteAssetBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button backupBtn;
    @FXML
    private Button restoreBtn;
    @FXML
    private Label userInfoLabel;
    @FXML
    private Button logoutBtn;
    
    // Current user information
    private UserInfo currentUser;

    public void initialize() {
        dbHandler = new UnifiedDatabaseHandler();
        try {
            dbHandler.initDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Initialize ComboBoxes
        roomTypeCombo.setItems(FXCollections.observableArrayList("Lab", "Office", "Classroom"));
        assetTypeCombo.setItems(FXCollections.observableArrayList("Chair", "Projector", "AC Unit"));
        assetConditionCombo.setItems(FXCollections.observableArrayList("Good", "Broken", "Under Maintenance"));
        filterTypeCombo.setItems(FXCollections.observableArrayList("Building", "Room", "Asset"));

        // Initialize Table Columns
        buildingIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        buildingNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        roomIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        roomNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roomTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        roomBuildingCol.setCellValueFactory(cellData -> cellData.getValue().buildingNameProperty());
        roomAvailabilityCol.setCellValueFactory(cellData -> cellData.getValue().availableProperty());

        assetIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        assetNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        assetTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        assetRoomCol.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        assetConditionCol.setCellValueFactory(cellData -> cellData.getValue().conditionProperty());

        searchIdCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Building) return ((Building) cellData.getValue()).idProperty();
            if (cellData.getValue() instanceof Room) return ((Room) cellData.getValue()).idProperty();
            if (cellData.getValue() instanceof Asset) return ((Asset) cellData.getValue()).idProperty();
            return null;
        });
        searchNameCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Building) return ((Building) cellData.getValue()).nameProperty();
            if (cellData.getValue() instanceof Room) return ((Room) cellData.getValue()).nameProperty();
            if (cellData.getValue() instanceof Asset) return ((Asset) cellData.getValue()).nameProperty();
            return null;
        });
        searchTypeCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Building) return new javafx.beans.property.SimpleStringProperty("Building");
            if (cellData.getValue() instanceof Room) return ((Room) cellData.getValue()).typeProperty();
            if (cellData.getValue() instanceof Asset) return ((Asset) cellData.getValue()).typeProperty();
            return null;
        });
        searchDetailsCol.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Building) return new javafx.beans.property.SimpleStringProperty("");
            if (cellData.getValue() instanceof Room) return ((Room) cellData.getValue()).buildingNameProperty();
            if (cellData.getValue() instanceof Asset) return ((Asset) cellData.getValue()).roomNameProperty();
            return null;
        });

        // Load data
        try {
            buildingTable.setItems(dbHandler.getBuildings());
            roomTable.setItems(dbHandler.getRooms());
            assetTable.setItems(dbHandler.getAssets());
            roomBuildingCombo.setItems(dbHandler.getBuildings());
            assetRoomCombo.setItems(dbHandler.getRooms());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize the main controller with user information
     * This method is called after login to set up the interface for the specific user
     */
    public void initData(UserInfo userInfo) {
        System.out.println("Initializing MainController for user: " + userInfo.fullName + " (Role: " + userInfo.role + ")");
        
        // Store current user information
        this.currentUser = userInfo;
        
        // Update user info label
        userInfoLabel.setText("Welcome, " + userInfo.fullName + " (" + userInfo.role.toUpperCase() + ")");
        
        try {
            // Refresh all data to ensure we have the latest information
            refreshAllData();
            
            // Configure interface based on user role
            configureInterfaceForUser(userInfo);
            
            System.out.println("MainController initialization completed successfully");
        } catch (Exception e) {
            System.err.println("Error initializing MainController: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Refresh all data tables
     */
    private void refreshAllData() throws SQLException {
        System.out.println("Refreshing all data tables...");
        buildingTable.setItems(dbHandler.getBuildings());
        roomTable.setItems(dbHandler.getRooms());
        assetTable.setItems(dbHandler.getAssets());
        roomBuildingCombo.setItems(dbHandler.getBuildings());
        assetRoomCombo.setItems(dbHandler.getRooms());
        System.out.println("Data refresh completed");
    }
    
    /**
     * Configure interface based on user role
     */
    private void configureInterfaceForUser(UserInfo userInfo) {
        if (userInfo.role.equals("user")) {
            // For regular users, disable editing capabilities
            addBuildingBtn.setDisable(true);
            editBuildingBtn.setDisable(true);
            deleteBuildingBtn.setDisable(true);
            
            addRoomBtn.setDisable(true);
            editRoomBtn.setDisable(true);
            deleteRoomBtn.setDisable(true);
            
            addAssetBtn.setDisable(true);
            editAssetBtn.setDisable(true);
            deleteAssetBtn.setDisable(true);
            
            backupBtn.setDisable(true);
            restoreBtn.setDisable(true);
            
            System.out.println("Interface configured for regular user (read-only access)");
        } else {
            // Admin users have full access
            System.out.println("Interface configured for admin user (full access)");
        }
    }

    @FXML
    private void addBuilding(ActionEvent event) {
        String name = buildingNameField.getText();
        if (!name.isEmpty()) {
            try {
                dbHandler.addBuilding(name);
                buildingTable.setItems(dbHandler.getBuildings());
                roomBuildingCombo.setItems(dbHandler.getBuildings());
                buildingNameField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void editBuilding(ActionEvent event) {
        Building selected = buildingTable.getSelectionModel().getSelectedItem();
        if (selected != null && !buildingNameField.getText().isEmpty()) {
            try {
                dbHandler.updateBuilding(selected.getId(), buildingNameField.getText());
                buildingTable.setItems(dbHandler.getBuildings());
                roomBuildingCombo.setItems(dbHandler.getBuildings());
                buildingNameField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteBuilding(ActionEvent event) {
        Building selected = buildingTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                dbHandler.deleteBuilding(selected.getId());
                buildingTable.setItems(dbHandler.getBuildings());
                roomBuildingCombo.setItems(dbHandler.getBuildings());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addRoom(ActionEvent event) {
        String name = roomNameField.getText();
        String type = roomTypeCombo.getValue();
        Building building = roomBuildingCombo.getValue();
        if (!name.isEmpty() && type != null && building != null) {
            try {
                dbHandler.addRoom(name, type, building.getId(), true);
                roomTable.setItems(dbHandler.getRooms());
                assetRoomCombo.setItems(dbHandler.getRooms());
                roomNameField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void editRoom(ActionEvent event) {
        Room selected = roomTable.getSelectionModel().getSelectedItem();
        if (selected != null && !roomNameField.getText().isEmpty() && roomTypeCombo.getValue() != null && roomBuildingCombo.getValue() != null) {
            try {
                dbHandler.updateRoom(selected.getId(), roomNameField.getText(), roomTypeCombo.getValue(), roomBuildingCombo.getValue().getId(), selected.isAvailable());
                roomTable.setItems(dbHandler.getRooms());
                assetRoomCombo.setItems(dbHandler.getRooms());
                roomNameField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteRoom(ActionEvent event) {
        Room selected = roomTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                dbHandler.deleteRoom(selected.getId());
                roomTable.setItems(dbHandler.getRooms());
                assetRoomCombo.setItems(dbHandler.getRooms());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void addAsset(ActionEvent event) {
        String name = assetNameField.getText();
        String type = assetTypeCombo.getValue();
        Room room = assetRoomCombo.getValue();
        String condition = assetConditionCombo.getValue();
        if (!name.isEmpty() && type != null && room != null && condition != null) {
            try {
                dbHandler.addAsset(name, type, room.getId(), condition);
                assetTable.setItems(dbHandler.getAssets());
                assetNameField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void editAsset(ActionEvent event) {
        Asset selected = assetTable.getSelectionModel().getSelectedItem();
        if (selected != null && !assetNameField.getText().isEmpty() && assetTypeCombo.getValue() != null && assetRoomCombo.getValue() != null && assetConditionCombo.getValue() != null) {
            try {
                dbHandler.updateAsset(selected.getId(), assetNameField.getText(), assetTypeCombo.getValue(), assetRoomCombo.getValue().getId(), assetConditionCombo.getValue());
                assetTable.setItems(dbHandler.getAssets());
                assetNameField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteAsset(ActionEvent event) {
        Asset selected = assetTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                dbHandler.deleteAsset(selected.getId());
                assetTable.setItems(dbHandler.getAssets());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void search(ActionEvent event) {
        String query = searchField.getText();
        String type = filterTypeCombo.getValue();
        try {
            searchTable.setItems(dbHandler.search(query, type));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFloorPlan(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FloorPlan.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Floor Plan");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backupDatabase(ActionEvent event) {
        try {
            dbHandler.backupDatabase();
            showAlert("Backup successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Backup failed!");
        }
    }

    @FXML
    private void restoreDatabase(ActionEvent event) {
        try {
            dbHandler.restoreDatabase();
            buildingTable.setItems(dbHandler.getBuildings());
            roomTable.setItems(dbHandler.getRooms());
            assetTable.setItems(dbHandler.getAssets());
            roomBuildingCombo.setItems(dbHandler.getBuildings());
            assetRoomCombo.setItems(dbHandler.getRooms());
            showAlert("Restore successful!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Restore failed!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Handle logout action - return to login screen
     */
    @FXML
    private void logout() {
        try {
            System.out.println("User " + currentUser.fullName + " is logging out...");
            
            // Load login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            
            // Switch to login scene
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Property Asset Manager - Login");
            stage.setMaximized(false);
            stage.centerOnScreen();
            
            System.out.println("Successfully returned to login screen");
            
        } catch (Exception e) {
            System.err.println("Error during logout: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error during logout: " + e.getMessage());
        }
    }
}