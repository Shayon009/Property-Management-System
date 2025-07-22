package propertyassetmanager;

import javafx.collections.ObservableList;
import java.sql.SQLException;

public class UnifiedDatabaseHandler {
    private DatabaseHandler sqliteHandler;
    private MySQLDatabaseHandler mysqlHandler;
    
    public UnifiedDatabaseHandler() {
        sqliteHandler = new DatabaseHandler();
        mysqlHandler = new MySQLDatabaseHandler();
    }
    
    private Object getCurrentHandler() {
        return DatabaseConfig.useMySQL() ? mysqlHandler : sqliteHandler;
    }
    
    public void initDatabase() throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.initDatabase();
        } else {
            sqliteHandler.initDatabase();
        }
        
        // Add sample data if tables are empty
        populateSampleDataIfEmpty();
    }
    
    /**
     * Populate sample data if the database is empty
     */
    private void populateSampleDataIfEmpty() throws SQLException {
        try {
            ObservableList<Building> buildings = getBuildings();
            if (buildings.isEmpty()) {
                System.out.println("Database is empty, populating with sample data...");
                
                // Add sample buildings
                addBuilding("Main Building");
                addBuilding("Warehouse A");
                addBuilding("Office Complex B");
                addBuilding("Laboratory Wing");
                addBuilding("Storage Facility C");
                
                // Get the buildings to get their IDs
                buildings = getBuildings();
                
                // Add sample rooms
                addRoom("Conference Room 1", "Meeting Room", buildings.get(0).getId(), true);
                addRoom("Storage Room 1", "Storage", buildings.get(1).getId(), true);
                addRoom("Office 101", "Office", buildings.get(2).getId(), true);
                addRoom("Reception Area", "Reception", buildings.get(0).getId(), true);
                addRoom("Lab Room A", "Lab", buildings.get(3).getId(), true);
                addRoom("Lab Room B", "Lab", buildings.get(3).getId(), true);
                addRoom("Classroom 201", "Classroom", buildings.get(0).getId(), true);
                addRoom("Storage Room 2", "Storage", buildings.get(4).getId(), true);
                
                // Get the rooms to get their IDs
                ObservableList<Room> rooms = getRooms();
                
                // Add sample assets
                addAsset("Projector XYZ", "Electronics", rooms.get(0).getId(), "Good");
                addAsset("Storage Rack #1", "Furniture", rooms.get(1).getId(), "Excellent");
                addAsset("Desktop Computer", "Electronics", rooms.get(2).getId(), "Fair");
                addAsset("Reception Desk", "Furniture", rooms.get(3).getId(), "Good");
                addAsset("Microscope Model A", "Electronics", rooms.get(4).getId(), "Excellent");
                addAsset("Lab Table Set", "Furniture", rooms.get(4).getId(), "Good");
                addAsset("Whiteboard", "Furniture", rooms.get(6).getId(), "Good");
                addAsset("Air Conditioning Unit", "AC Unit", rooms.get(0).getId(), "Under Maintenance");
                addAsset("Office Chair Set", "Chair", rooms.get(2).getId(), "Good");
                addAsset("Lab Equipment Cabinet", "Furniture", rooms.get(5).getId(), "Excellent");
                addAsset("Network Switch", "Electronics", rooms.get(2).getId(), "Good");
                addAsset("Fire Extinguisher", "Safety", rooms.get(1).getId(), "Good");
                
                System.out.println("Sample data populated successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error populating sample data: " + e.getMessage());
            // Don't throw exception here, just log it
        }
    }
    
    public void setupUsersTable() {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.setupUsersTable();
        } else {
            sqliteHandler.setupUsersTable();
        }
    }
    
    public boolean authenticateUser(String username, String password) {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.authenticateUser(username, password);
        } else {
            return sqliteHandler.authenticateUser(username, password);
        }
    }
    
    public UserInfo getUserInfo(String username, String password) {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.getUserInfo(username, password);
        } else {
            // For SQLite, we need to implement getUserInfo method
            String sql = "SELECT full_name, role FROM users WHERE username = ? AND password = ?";
            try (java.sql.Connection conn = sqliteHandler.connect();
                 java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                java.sql.ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.username = username;
                    userInfo.fullName = rs.getString("full_name");
                    userInfo.role = rs.getString("role");
                    return userInfo;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    
    public void createUser(String fullName, String username, String password, String role) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.createUser(fullName, username, password, role);
        } else {
            // Implement for SQLite
            String sql = "INSERT INTO users (full_name, username, password, role) VALUES (?, ?, ?, ?)";
            try (java.sql.Connection conn = sqliteHandler.connect();
                 java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, fullName);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, role);
                pstmt.executeUpdate();
            }
        }
    }
    
    public boolean usernameExists(String username) {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.usernameExists(username);
        } else {
            // Implement for SQLite
            String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
            try (java.sql.Connection conn = sqliteHandler.connect();
                 java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                java.sql.ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
    
    // Building methods
    public ObservableList<Building> getBuildings() throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.getBuildings();
        } else {
            return sqliteHandler.getBuildings();
        }
    }
    
    public void addBuilding(String name) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.addBuilding(name);
        } else {
            sqliteHandler.addBuilding(name);
        }
    }
    
    public void updateBuilding(int id, String name) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.updateBuilding(id, name);
        } else {
            sqliteHandler.updateBuilding(id, name);
        }
    }
    
    public void deleteBuilding(int id) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.deleteBuilding(id);
        } else {
            sqliteHandler.deleteBuilding(id);
        }
    }
    
    // Room methods
    public ObservableList<Room> getRooms() throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.getRooms();
        } else {
            return sqliteHandler.getRooms();
        }
    }
    
    public Room getRoomByName(String name) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.getRoomByName(name);
        } else {
            return sqliteHandler.getRoomByName(name);
        }
    }
    
    public void addRoom(String name, String type, int buildingId, boolean available) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.addRoom(name, type, buildingId, available);
        } else {
            sqliteHandler.addRoom(name, type, buildingId, available);
        }
    }
    
    public void updateRoom(int id, String name, String type, int buildingId, boolean available) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.updateRoom(id, name, type, buildingId, available);
        } else {
            sqliteHandler.updateRoom(id, name, type, buildingId, available);
        }
    }
    
    public void deleteRoom(int id) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.deleteRoom(id);
        } else {
            sqliteHandler.deleteRoom(id);
        }
    }
    
    // Asset methods
    public ObservableList<Asset> getAssets() throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.getAssets();
        } else {
            return sqliteHandler.getAssets();
        }
    }
    
    public ObservableList<Asset> getAssetsInRoom(int roomId) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.getAssetsInRoom(roomId);
        } else {
            return sqliteHandler.getAssetsInRoom(roomId);
        }
    }
    
    public void addAsset(String name, String type, int roomId, String condition) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.addAsset(name, type, roomId, condition);
        } else {
            sqliteHandler.addAsset(name, type, roomId, condition);
        }
    }
    
    public void updateAsset(int id, String name, String type, int roomId, String condition) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.updateAsset(id, name, type, roomId, condition);
        } else {
            sqliteHandler.updateAsset(id, name, type, roomId, condition);
        }
    }
    
    public void deleteAsset(int id) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.deleteAsset(id);
        } else {
            sqliteHandler.deleteAsset(id);
        }
    }
    
    // Search method
    public ObservableList<Object> search(String query, String type) throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            return mysqlHandler.search(query, type);
        } else {
            return sqliteHandler.search(query, type);
        }
    }
    
    // Backup and restore
    public void backupDatabase() throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.backupDatabase();
        } else {
            sqliteHandler.backupDatabase();
        }
    }
    
    public void restoreDatabase() throws SQLException {
        if (DatabaseConfig.useMySQL()) {
            mysqlHandler.restoreDatabase();
        } else {
            sqliteHandler.restoreDatabase();
        }
    }
}
