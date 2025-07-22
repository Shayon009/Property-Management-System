# Property Asset Manager - MySQL Setup Instructions

## Prerequisites
1. Install XAMPP from https://www.apachefriends.org/
2. Start Apache and MySQL services in XAMPP Control Panel

## Database Setup

### Option 1: Using phpMyAdmin (Recommended)
1. Open your web browser and go to `http://localhost/phpmyadmin/`
2. Click on "Import" tab
3. Choose the file `setup_mysql_database.sql` from your project folder
4. Click "Go" to execute the script
5. The database `property_asset_manager` will be created with sample data

### Option 2: Using MySQL Command Line
1. Open Command Prompt/Terminal
2. Navigate to MySQL bin directory (usually `C:\xampp\mysql\bin\`)
3. Run: `mysql -u root -p`
4. Execute: `source path/to/your/project/setup_mysql_database.sql`

## Application Configuration

### Method 1: Using Database Switcher (GUI)
1. Compile and run `DatabaseSwitcher.java` class
2. Select "MySQL (XAMPP)" option
3. Configure the settings:
   - Host: localhost
   - Port: 3306
   - Database: property_asset_manager
   - Username: root
   - Password: (leave empty for default XAMPP)
4. Click "Test Connection" to verify
5. Click "Save Configuration"

### Method 2: Manual Configuration
1. Create a file named `database.properties` in your project root
2. Add the following content:
```
use.mysql=true
mysql.host=localhost
mysql.port=3306
mysql.database=property_asset_manager
mysql.username=root
mysql.password=
```

## Running the Application

1. Make sure XAMPP MySQL service is running
2. Compile and run the main application
3. Use these default credentials:
   - Username: `admin`
   - Password: `admin123`

## Database Features

### Tables Created:
- **buildings**: Stores building information
- **rooms**: Stores room information with building relationships
- **assets**: Stores asset information with room relationships  
- **users**: Stores user authentication information

### Sample Data Included:
- 3 Buildings (Main Building, Warehouse A, Office Complex B)
- 4 Rooms (Conference Room 1, Storage Room 1, Office 101, Reception Area)
- 4 Assets (Projector, Storage Rack, Desktop Computer, Reception Desk)
- 1 Admin User (admin/admin123)

## Switching Between Databases

The application supports both SQLite and MySQL. You can switch between them at any time:

1. Run the `DatabaseSwitcher` utility
2. Select your preferred database type
3. Configure connection settings if using MySQL
4. Save the configuration

## Troubleshooting

### Common Issues:

1. **"Connection refused" error**
   - Make sure XAMPP MySQL service is running
   - Check if port 3306 is available

2. **"Access denied" error**
   - Verify username/password in configuration
   - Default XAMPP credentials are username: `root`, password: (empty)

3. **"Database not found" error**
   - Run the `setup_mysql_database.sql` script
   - Make sure the database name matches in configuration

4. **"JDBC Driver not found" error**
   - Ensure `mysql-connector-j-8.4.0.jar` is in the lib folder
   - Check that the JAR is included in project classpath

### Performance Tips:
- MySQL provides better performance for larger datasets
- SQLite is suitable for single-user, lightweight applications
- Use MySQL for multi-user environments

## Backup and Restore

The application includes built-in backup/restore functionality:
- Backup files are saved as SQL scripts
- MySQL backups include all table data and structure
- Restore functionality can recreate the database from backup files

## Security Considerations

- Change default admin password after first login
- Use strong passwords for MySQL users in production
- Consider implementing password hashing for production use
- Restrict database access to authorized users only
