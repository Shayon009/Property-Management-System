package propertyassetmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

public class UserPanelController {
    @FXML private Label welcomeLabel;
    
    // Building table components
    @FXML private TableView<Building> buildingTable;
    @FXML private TableColumn<Building, Number> buildingIdCol;
    @FXML private TableColumn<Building, String> buildingNameCol;
    @FXML private TableColumn<Building, String> buildingCreatedCol;
    
    // Room table components
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, Number> roomIdCol;
    @FXML private TableColumn<Room, String> roomNameCol;
    @FXML private TableColumn<Room, String> roomTypeCol;
    @FXML private TableColumn<Room, String> roomBuildingCol;
    @FXML private TableColumn<Room, Boolean> roomAvailabilityCol;
    
    // Asset table components
    @FXML private TableView<Asset> assetTable;
    @FXML private TableColumn<Asset, Number> assetIdCol;
    @FXML private TableColumn<Asset, String> assetNameCol;
    @FXML private TableColumn<Asset, String> assetTypeCol;
    @FXML private TableColumn<Asset, String> assetRoomCol;
    @FXML private TableColumn<Asset, String> assetConditionCol;
    
    // Search components
    @FXML private TextField searchField;
    @FXML private TableView<Object> searchTable;
    @FXML private TableColumn<Object, String> searchIdCol;
    @FXML private TableColumn<Object, String> searchNameCol;
    @FXML private TableColumn<Object, String> searchTypeCol;
    @FXML private TableColumn<Object, String> searchDetailsCol;

    private UnifiedDatabaseHandler dbHandler = new UnifiedDatabaseHandler();
    private UserInfo currentUser;

    @FXML
    private void initialize() {
        try {
            // Initialize database connection
            dbHandler.initDatabase();
            
            // Setup table columns
            setupTableColumns();
            
            // Load initial data
            loadAllData();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error initializing user panel: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        // Building table columns
        buildingIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        buildingNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        // Remove the createdAt column since Building class doesn't have this property
        buildingCreatedCol.setCellValueFactory(cellData -> new SimpleStringProperty("N/A"));

        // Room table columns
        roomIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        roomNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        roomTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        roomBuildingCol.setCellValueFactory(cellData -> cellData.getValue().buildingNameProperty());
        roomAvailabilityCol.setCellValueFactory(cellData -> cellData.getValue().availableProperty());

        // Asset table columns
        assetIdCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        assetNameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        assetTypeCol.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        assetRoomCol.setCellValueFactory(cellData -> cellData.getValue().roomNameProperty());
        assetConditionCol.setCellValueFactory(cellData -> cellData.getValue().conditionProperty());
    }

    private void loadAllData() {
        try {
            // Load buildings - use getBuildings() not getAllBuildings()
            ObservableList<Building> buildings = dbHandler.getBuildings();
            buildingTable.setItems(buildings);

            // Load rooms - use getRooms() not getAllRooms()
            ObservableList<Room> rooms = dbHandler.getRooms();
            roomTable.setItems(rooms);

            // Load assets - use getAssets() not getAllAssets()
            ObservableList<Asset> assets = dbHandler.getAssets();
            assetTable.setItems(assets);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public void initData(UserInfo userInfo) {
        this.currentUser = userInfo;
        if (userInfo != null) {
            welcomeLabel.setText("Welcome, " + userInfo.fullName + " (User - Read Only Access)");
        } else {
            welcomeLabel.setText("Welcome, User (Read Only Access)");
        }
        
        // Refresh data when user logs in
        loadAllData();
    }

    @FXML
    private void search() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            return;
        }

        try {
            // Simple search implementation - you can enhance this
            ObservableList<Object> searchResults = FXCollections.observableArrayList();
            
            // Search buildings
            for (Building building : dbHandler.getBuildings()) {
                if (building.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    String.valueOf(building.getId()).contains(searchText)) {
                    searchResults.add(building);
                }
            }
            
            // Search rooms
            for (Room room : dbHandler.getRooms()) {
                if (room.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    String.valueOf(room.getId()).contains(searchText)) {
                    searchResults.add(room);
                }
            }
            
            // Search assets
            for (Asset asset : dbHandler.getAssets()) {
                if (asset.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                    String.valueOf(asset.getId()).contains(searchText)) {
                    searchResults.add(asset);
                }
            }
            
            searchTable.setItems(searchResults);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during search: " + e.getMessage());
        }
    }

    @FXML
    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Property Asset Manager - Login");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error logging out: " + e.getMessage());
        }
    }
} 