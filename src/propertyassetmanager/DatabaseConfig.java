package propertyassetmanager;

import java.io.*;
import java.util.Properties;

public class DatabaseConfig {
    private static final String CONFIG_FILE = "database.properties";
    private static Properties properties = new Properties();
    private static boolean useMySQL = false;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
                useMySQL = Boolean.parseBoolean(properties.getProperty("use.mysql", "false"));
            } catch (IOException e) {
                System.err.println("Error loading database configuration: " + e.getMessage());
                createDefaultConfig();
            }
        } else {
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        properties.setProperty("use.mysql", "false");
        properties.setProperty("mysql.host", "localhost");
        properties.setProperty("mysql.port", "3306");
        properties.setProperty("mysql.database", "property_asset_manager");
        properties.setProperty("mysql.username", "root");
        properties.setProperty("mysql.password", "");
        
        saveConfig();
    }

    public static void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Database Configuration");
        } catch (IOException e) {
            System.err.println("Error saving database configuration: " + e.getMessage());
        }
    }

    public static boolean useMySQL() {
        return useMySQL;
    }

    public static void setUseMySQL(boolean use) {
        useMySQL = use;
        properties.setProperty("use.mysql", String.valueOf(use));
        saveConfig();
    }

    public static String getMySQLHost() {
        return properties.getProperty("mysql.host", "localhost");
    }

    public static String getMySQLPort() {
        return properties.getProperty("mysql.port", "3306");
    }

    public static String getMySQLDatabase() {
        return properties.getProperty("mysql.database", "property_asset_manager");
    }

    public static String getMySQLUsername() {
        return properties.getProperty("mysql.username", "root");
    }

    public static String getMySQLPassword() {
        return properties.getProperty("mysql.password", "");
    }

    public static void setMySQLConfig(String host, String port, String database, String username, String password) {
        properties.setProperty("mysql.host", host);
        properties.setProperty("mysql.port", port);
        properties.setProperty("mysql.database", database);
        properties.setProperty("mysql.username", username);
        properties.setProperty("mysql.password", password);
        saveConfig();
    }
}
