package propertyassetmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:property_manager.db";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void initDatabase() throws SQLException {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS buildings (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS rooms (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT, building_id INTEGER, available BOOLEAN, FOREIGN KEY(building_id) REFERENCES buildings(id))");
            stmt.execute("CREATE TABLE IF NOT EXISTS assets (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT, room_id INTEGER, condition TEXT, FOREIGN KEY(room_id) REFERENCES rooms(id))");
        }
    }

    public ObservableList<Building> getBuildings() throws SQLException {
        ObservableList<Building> buildings = FXCollections.observableArrayList();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM buildings")) {
            while (rs.next()) {
                buildings.add(new Building(rs.getInt("id"), rs.getString("name")));
            }
        }
        return buildings;
    }

    public void addBuilding(String name) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO buildings (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void updateBuilding(int id, String name) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("UPDATE buildings SET name = ? WHERE id = ?")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteBuilding(int id) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM buildings WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ObservableList<Room> getRooms() throws SQLException {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT r.*, b.name AS building_name FROM rooms r JOIN buildings b ON r.building_id = b.id")) {
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("building_id"), rs.getString("building_name"), rs.getBoolean("available")));
            }
        }
        return rooms;
    }

    public Room getRoomByName(String name) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT r.*, b.name AS building_name FROM rooms r JOIN buildings b ON r.building_id = b.id WHERE r.name = ?")) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Room(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("building_id"), rs.getString("building_name"), rs.getBoolean("available"));
            }
        }
        return null;
    }

    public void addRoom(String name, String type, int buildingId, boolean available) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO rooms (name, type, building_id, available) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, buildingId);
            pstmt.setBoolean(4, available);
            pstmt.executeUpdate();
        }
    }

    public void updateRoom(int id, String name, String type, int buildingId, boolean available) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("UPDATE rooms SET name = ?, type = ?, building_id = ?, available = ? WHERE id = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, buildingId);
            pstmt.setBoolean(4, available);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteRoom(int id) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM rooms WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ObservableList<Asset> getAssets() throws SQLException {
        ObservableList<Asset> assets = FXCollections.observableArrayList();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT a.*, r.name AS room_name FROM assets a JOIN rooms r ON a.room_id = r.id")) {
            while (rs.next()) {
                assets.add(new Asset(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("room_id"), rs.getString("room_name"), rs.getString("condition")));
            }
        }
        return assets;
    }

    public ObservableList<Asset> getAssetsInRoom(int roomId) throws SQLException {
        ObservableList<Asset> assets = FXCollections.observableArrayList();
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT a.*, r.name AS room_name FROM assets a JOIN rooms r ON a.room_id = r.id WHERE a.room_id = ?")) {
            pstmt.setInt(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                assets.add(new Asset(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("room_id"), rs.getString("room_name"), rs.getString("condition")));
            }
        }
        return assets;
    }

    public void addAsset(String name, String type, int roomId, String condition) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO assets (name, type, room_id, condition) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, roomId);
            pstmt.setString(4, condition);
            pstmt.executeUpdate();
        }
    }

    public void updateAsset(int id, String name, String type, int roomId, String condition) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("UPDATE assets SET name = ?, type = ?, room_id = ?, condition = ? WHERE id = ?")) {
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setInt(3, roomId);
            pstmt.setString(4, condition);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteAsset(int id) throws SQLException {
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM assets WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ObservableList<Object> search(String query, String type) throws SQLException {
        ObservableList<Object> results = FXCollections.observableArrayList();
        if (type == null || type.equals("Building")) {
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM buildings WHERE name LIKE ? OR id = ?")) {
                pstmt.setString(1, "%" + query + "%");
                pstmt.setString(2, query);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    results.add(new Building(rs.getInt("id"), rs.getString("name")));
                }
            }
        }
        if (type == null || type.equals("Room")) {
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT r.*, b.name AS building_name FROM rooms r JOIN buildings b ON r.building_id = b.id WHERE r.name LIKE ? OR r.id = ?")) {
                pstmt.setString(1, "%" + query + "%");
                pstmt.setString(2, query);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    results.add(new Room(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("building_id"), rs.getString("building_name"), rs.getBoolean("available")));
                }
            }
        }
        if (type == null || type.equals("Asset")) {
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement("SELECT a.*, r.name AS room_name FROM assets a JOIN rooms r ON a.room_id = r.id WHERE a.name LIKE ? OR a.id = ?")) {
                pstmt.setString(1, "%" + query + "%");
                pstmt.setString(2, query);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    results.add(new Asset(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getInt("room_id"), rs.getString("room_name"), rs.getString("condition")));
                }
            }
        }
        return results;
    }

    public void backupDatabase() throws SQLException {
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table'")) {
            File backupFile = new File("property_manager_backup.sql");
            try (PrintWriter writer = new PrintWriter(backupFile)) {
                while (rs.next()) {
                    String table = rs.getString("name");
                    try (ResultSet tableData = stmt.executeQuery("SELECT * FROM " + table)) {
                        writer.println("DELETE FROM " + table + ";");
                        while (tableData.next()) {
                            StringBuilder insert = new StringBuilder("INSERT INTO " + table + " VALUES (");
                            for (int i = 1; i <= tableData.getMetaData().getColumnCount(); i++) {
                                insert.append("'").append(tableData.getString(i)).append("'");
                                if (i < tableData.getMetaData().getColumnCount()) insert.append(",");
                            }
                            insert.append(");");
                            writer.println(insert);
                        }
                    }
                }
            } catch (IOException e) {
                throw new SQLException("Backup failed: " + e.getMessage());
            }
        }
    }

    public void restoreDatabase() throws SQLException {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM assets");
            stmt.execute("DELETE FROM rooms");
            stmt.execute("DELETE FROM buildings");
            File backupFile = new File("property_manager_backup.sql");
            if (backupFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(backupFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().isEmpty()) {
                            stmt.execute(line);
                        }
                    }
                } catch (IOException e) {
                    throw new SQLException("Restore failed: " + e.getMessage());
                }
            }
        }
    }

    // User authentication and admin setup
    public void setupUsersTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                full_name TEXT NOT NULL,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL CHECK(role IN ('admin', 'user')),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            
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
            pstmt.setString(3, "admin123"); // In a real application, this should be hashed
            pstmt.setString(4, "admin");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}