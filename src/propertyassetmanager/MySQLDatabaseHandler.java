package propertyassetmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;

public class MySQLDatabaseHandler {
    
    public Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s:%s/%s", 
                DatabaseConfig.getMySQLHost(),
                DatabaseConfig.getMySQLPort(),
                DatabaseConfig.getMySQLDatabase());
            return DriverManager.getConnection(url, 
                DatabaseConfig.getMySQLUsername(), 
                DatabaseConfig.getMySQLPassword());
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public void initDatabase() throws SQLException {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            // Create buildings table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS buildings (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            // Create rooms table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS rooms (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    type VARCHAR(100),
                    building_id INT,
                    available BOOLEAN DEFAULT TRUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (building_id) REFERENCES buildings(id) ON DELETE CASCADE
                )
            """);

            // Create assets table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS assets (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    type VARCHAR(100),
                    room_id INT,
                    condition_status VARCHAR(100),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
                )
            """);

            // Create users table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    full_name VARCHAR(255) NOT NULL,
                    username VARCHAR(100) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    role ENUM('admin', 'user') NOT NULL DEFAULT 'user',
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            System.out.println("Database tables created successfully!");
        }
    }

    public ObservableList<Building> getBuildings() throws SQLException {
        ObservableList<Building> buildings = FXCollections.observableArrayList();
        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("SELECT * FROM buildings ORDER BY name")) {
            while (rs.next()) {
                buildings.add(new Building(rs.getInt("id"), rs.getString("name")));
            }
        }
        return buildings;
    }

    public void addBuilding(String name) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO buildings (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void updateBuilding(int id, String name) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("UPDATE buildings SET name = ? WHERE id = ?")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteBuilding(int id) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM buildings WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ObservableList<Room> getRooms() throws SQLException {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("""
                SELECT r.*, b.name AS building_name 
                FROM rooms r 
                JOIN buildings b ON r.building_id = b.id 
                ORDER BY b.name, r.name
             """)) {
            while (rs.next()) {
                rooms.add(new Room(
                    rs.getInt("id"), 
                    rs.getString("name"), 
                    rs.getString("type"), 
                    rs.getInt("building_id"), 
                    rs.getString("building_name"), 
                    rs.getBoolean("available")
                ));
            }
        }
        return rooms;
    }

    public Room getRoomByName(String name) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("""
                SELECT r.*, b.name AS building_name 
                FROM rooms r 
                JOIN buildings b ON r.building_id = b.id 
                WHERE r.name = ?
             """)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Room(
                    rs.getInt("id"), 
                    rs.getString("name"), 
                    rs.getString("type"), 
                    rs.getInt("building_id"), 
                    rs.getString("building_name"), 
                    rs.getBoolean("available")
                );
            }
        }
        return null;
    }

    public void addRoom(String name, String type, int buildingId, boolean available) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("""
                INSERT INTO rooms (name, type, building_id, available) VALUES (?, ?, ?, ?)
             """)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, buildingId);
            pstmt.setBoolean(4, available);
            pstmt.executeUpdate();
        }
    }

    public void updateRoom(int id, String name, String type, int buildingId, boolean available) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("""
                UPDATE rooms SET name = ?, type = ?, building_id = ?, available = ? WHERE id = ?
             """)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, buildingId);
            pstmt.setBoolean(4, available);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteRoom(int id) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM rooms WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ObservableList<Asset> getAssets() throws SQLException {
        ObservableList<Asset> assets = FXCollections.observableArrayList();
        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("""
                SELECT a.*, r.name AS room_name 
                FROM assets a 
                JOIN rooms r ON a.room_id = r.id 
                ORDER BY r.name, a.name
             """)) {
            while (rs.next()) {
                assets.add(new Asset(
                    rs.getInt("id"), 
                    rs.getString("name"), 
                    rs.getString("type"), 
                    rs.getInt("room_id"), 
                    rs.getString("room_name"), 
                    rs.getString("condition_status")
                ));
            }
        }
        return assets;
    }

    public ObservableList<Asset> getAssetsInRoom(int roomId) throws SQLException {
        ObservableList<Asset> assets = FXCollections.observableArrayList();
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("""
                SELECT a.*, r.name AS room_name 
                FROM assets a 
                JOIN rooms r ON a.room_id = r.id 
                WHERE a.room_id = ?
                ORDER BY a.name
             """)) {
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                assets.add(new Asset(
                    rs.getInt("id"), 
                    rs.getString("name"), 
                    rs.getString("type"), 
                    rs.getInt("room_id"), 
                    rs.getString("room_name"), 
                    rs.getString("condition_status")
                ));
            }
        }
        return assets;
    }

    public void addAsset(String name, String type, int roomId, String condition) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("""
                INSERT INTO assets (name, type, room_id, condition_status) VALUES (?, ?, ?, ?)
             """)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, roomId);
            pstmt.setString(4, condition);
            pstmt.executeUpdate();
        }
    }

    public void updateAsset(int id, String name, String type, int roomId, String condition) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("""
                UPDATE assets SET name = ?, type = ?, room_id = ?, condition_status = ? WHERE id = ?
             """)) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, roomId);
            pstmt.setString(4, condition);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteAsset(int id) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM assets WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ObservableList<Object> search(String query, String type) throws SQLException {
        ObservableList<Object> results = FXCollections.observableArrayList();
        
        if (type == null || type.equals("Building")) {
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement("""
                    SELECT * FROM buildings 
                    WHERE name LIKE ? OR id = ? 
                    ORDER BY name
                 """)) {
                pstmt.setString(1, "%" + query + "%");
                try {
                    pstmt.setInt(2, Integer.parseInt(query));
                } catch (NumberFormatException e) {
                    pstmt.setInt(2, -1); // Use -1 for non-numeric queries
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    results.add(new Building(rs.getInt("id"), rs.getString("name")));
                }
            }
        }
        
        if (type == null || type.equals("Room")) {
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement("""
                    SELECT r.*, b.name AS building_name 
                    FROM rooms r 
                    JOIN buildings b ON r.building_id = b.id 
                    WHERE r.name LIKE ? OR r.id = ?
                    ORDER BY b.name, r.name
                 """)) {
                pstmt.setString(1, "%" + query + "%");
                try {
                    pstmt.setInt(2, Integer.parseInt(query));
                } catch (NumberFormatException e) {
                    pstmt.setInt(2, -1);
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    results.add(new Room(
                        rs.getInt("id"), 
                        rs.getString("name"), 
                        rs.getString("type"), 
                        rs.getInt("building_id"), 
                        rs.getString("building_name"), 
                        rs.getBoolean("available")
                    ));
                }
            }
        }
        
        if (type == null || type.equals("Asset")) {
            try (Connection conn = connect(); 
                 PreparedStatement pstmt = conn.prepareStatement("""
                    SELECT a.*, r.name AS room_name 
                    FROM assets a 
                    JOIN rooms r ON a.room_id = r.id 
                    WHERE a.name LIKE ? OR a.id = ?
                    ORDER BY r.name, a.name
                 """)) {
                pstmt.setString(1, "%" + query + "%");
                try {
                    pstmt.setInt(2, Integer.parseInt(query));
                } catch (NumberFormatException e) {
                    pstmt.setInt(2, -1);
                }
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    results.add(new Asset(
                        rs.getInt("id"), 
                        rs.getString("name"), 
                        rs.getString("type"), 
                        rs.getInt("room_id"), 
                        rs.getString("room_name"), 
                        rs.getString("condition_status")
                    ));
                }
            }
        }
        
        return results;
    }

    public void backupDatabase() throws SQLException {
        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement()) {
            
            File backupFile = new File("property_manager_mysql_backup.sql");
            try (PrintWriter writer = new PrintWriter(backupFile)) {
                writer.println("-- MySQL Database Backup");
                writer.println("-- Generated on: " + new java.util.Date());
                writer.println();
                
                // Backup buildings
                writer.println("-- Buildings Table");
                writer.println("DELETE FROM assets;");
                writer.println("DELETE FROM rooms;");
                writer.println("DELETE FROM buildings;");
                
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM buildings")) {
                    while (rs.next()) {
                        writer.printf("INSERT INTO buildings (id, name) VALUES (%d, '%s');%n",
                            rs.getInt("id"), rs.getString("name").replace("'", "''"));
                    }
                }
                
                // Backup rooms
                writer.println("\n-- Rooms Table");
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM rooms")) {
                    while (rs.next()) {
                        writer.printf("INSERT INTO rooms (id, name, type, building_id, available) VALUES (%d, '%s', '%s', %d, %b);%n",
                            rs.getInt("id"), 
                            rs.getString("name").replace("'", "''"),
                            rs.getString("type").replace("'", "''"),
                            rs.getInt("building_id"),
                            rs.getBoolean("available"));
                    }
                }
                
                // Backup assets
                writer.println("\n-- Assets Table");
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM assets")) {
                    while (rs.next()) {
                        writer.printf("INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (%d, '%s', '%s', %d, '%s');%n",
                            rs.getInt("id"), 
                            rs.getString("name").replace("'", "''"),
                            rs.getString("type").replace("'", "''"),
                            rs.getInt("room_id"),
                            rs.getString("condition_status").replace("'", "''"));
                    }
                }
                
                System.out.println("Database backup created: " + backupFile.getAbsolutePath());
            } catch (IOException e) {
                throw new SQLException("Backup failed: " + e.getMessage());
            }
        }
    }

    public void restoreDatabase() throws SQLException {
        try (Connection conn = connect(); 
             Statement stmt = conn.createStatement()) {
            
            File backupFile = new File("property_manager_mysql_backup.sql");
            if (backupFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(backupFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (!line.isEmpty() && !line.startsWith("--")) {
                            stmt.execute(line);
                        }
                    }
                    System.out.println("Database restored from backup");
                } catch (IOException e) {
                    throw new SQLException("Restore failed: " + e.getMessage());
                }
            } else {
                throw new SQLException("Backup file not found");
            }
        }
    }

    // User authentication methods
    public void setupUsersTable() {
        try {
            initDatabase(); // This will create all tables including users
            
            // Create default admin if no users exist
            if (!doesAdminExist()) {
                createDefaultAdmin();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean doesAdminExist() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'admin'";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createDefaultAdmin() {
        String sql = "INSERT INTO users (full_name, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "System Administrator");
            pstmt.setString(2, "admin");
            pstmt.setString(3, "admin123"); // In production, this should be hashed
            pstmt.setString(4, "admin");
            pstmt.executeUpdate();
            System.out.println("Default admin user created: admin/admin123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserInfo getUserInfo(String username, String password) {
        String sql = "SELECT full_name, role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
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

    public void createUser(String fullName, String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, role);
            pstmt.executeUpdate();
        }
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
