# Property Asset Manager

A JavaFX-based application for managing property assets, buildings, rooms, and equipment inventory.

## ✅ Issues Fixed

1. **"Error loading application"** - Fixed FXML resource loading paths
2. **Database connectivity** - Added MySQL support for XAMPP
3. **Unified database handling** - Can switch between SQLite and MySQL seamlessly

## 🚀 Quick Start

### Prerequisites
- **NetBeans IDE** (recommended) or IntelliJ IDEA with JavaFX support
- **XAMPP** (optional, for MySQL database)
- **Java 11+** with JavaFX

### Option 1: Run with SQLite (Easiest)
1. Open the project in NetBeans IDE
2. Right-click on `Main.java` → Run File
3. Login with: `admin` / `admin123`

### Option 2: Run with MySQL (XAMPP)
1. Start XAMPP and ensure MySQL service is running
2. Open http://localhost/phpmyadmin/
3. Import the file `setup_mysql_database.sql`
4. Run `DatabaseSwitcher.java` to configure MySQL connection
5. Run `Main.java`

## 📁 Project Structure

```
PropertyAssetManager/
├── src/propertyassetmanager/
│   ├── Main.java                    # Application entry point
│   ├── LoginController.java         # Login screen logic
│   ├── AdminPanelController.java    # Admin interface
│   ├── UserPanelController.java     # User interface
│   ├── MainController.java          # Main application logic
│   ├── DatabaseHandler.java         # SQLite database operations
│   ├── MySQLDatabaseHandler.java    # MySQL database operations
│   ├── UnifiedDatabaseHandler.java  # Database abstraction layer
│   ├── DatabaseConfig.java          # Database configuration management
│   ├── DatabaseSwitcher.java        # GUI for switching databases
│   ├── DatabaseTest.java            # Database testing utility
│   └── *.fxml                       # UI layout files
├── lib/
│   ├── sqlite-jdbc-3.50.2.0.jar    # SQLite JDBC driver
│   └── mysql-connector-j-8.4.0.jar # MySQL JDBC driver
├── setup_mysql_database.sql         # MySQL database setup script
├── MySQL_Setup_Instructions.md      # Detailed MySQL setup guide
└── run_application.bat              # Windows batch file for setup help
```

## 🔧 Database Configuration

### SQLite (Default)
- No additional setup required
- Database file: `property_manager.db`
- Automatically created when application starts

### MySQL (XAMPP)
- Requires XAMPP with MySQL running
- Database name: `property_asset_manager`
- Default credentials: `root` / (empty password)
- Import `setup_mysql_database.sql` to create tables and sample data

### Switching Databases
Run `DatabaseSwitcher.java` to:
- Choose between SQLite and MySQL
- Configure MySQL connection settings
- Test database connections
- Save configuration preferences

## 📊 Database Schema

### Tables
- **buildings** - Building information
- **rooms** - Room details linked to buildings
- **assets** - Equipment/asset inventory linked to rooms
- **users** - User authentication and roles

### Sample Data Included
- 3 Buildings (Main Building, Warehouse A, Office Complex B)
- 4 Rooms (Conference Room 1, Storage Room 1, Office 101, Reception Area)
- 4 Assets (Projector, Storage Rack, Desktop Computer, Reception Desk)
- 1 Admin User (admin/admin123)

## 🔑 User Roles

### Admin
- Full access to all features
- Can manage buildings, rooms, and assets
- User management capabilities
- Backup and restore functions

### User
- Read-only access to asset information
- Search and view capabilities
- Cannot modify data

## 🛠️ Features

### Asset Management
- ✅ Add, edit, delete buildings
- ✅ Manage rooms within buildings
- ✅ Track assets and their conditions
- ✅ Search across all entities
- ✅ Backup and restore data

### User Management
- ✅ User authentication
- ✅ Role-based access control
- ✅ User registration (admin-controlled)

### Database Features
- ✅ SQLite and MySQL support
- ✅ Automatic database initialization
- ✅ Data migration capabilities
- ✅ Connection testing

## 🚨 Troubleshooting

### "Error loading application"
- **Fixed!** - FXML resource paths corrected
- Ensure all FXML files are in the same package as controllers

### "NullPointerException: Location is required"
- **Fixed!** - FXML file references updated
- Make sure JavaFX runtime is properly configured

### Database Connection Issues
- For MySQL: Ensure XAMPP MySQL service is running
- Check credentials in `database.properties`
- Use `DatabaseTest.java` to verify connection
- Run `DatabaseSwitcher.java` to reconfigure

### JavaFX Runtime Issues
- Use NetBeans IDE for easiest setup
- Or configure JavaFX module path in your IDE
- Ensure JavaFX libraries are in classpath

## 📝 Development Notes

### Code Improvements Made
1. **Unified Database Layer** - Single interface for both SQLite and MySQL
2. **Configuration Management** - Persistent database settings
3. **Error Handling** - Better exception handling and user feedback
4. **Resource Management** - Proper FXML resource loading
5. **Code Organization** - Cleaner separation of concerns

### Future Enhancements
- Password hashing for security
- Advanced reporting features
- Asset maintenance scheduling
- QR code integration for assets
- REST API for mobile access

## 📞 Support

If you encounter any issues:
1. Check the `MySQL_Setup_Instructions.md` for detailed setup steps
2. Run `DatabaseTest.java` to verify database connectivity
3. Use `DatabaseSwitcher.java` to reconfigure database settings
4. Ensure JavaFX runtime is properly configured in your IDE

---

**Default Login:** admin / admin123

Enjoy managing your property assets! 🏢📦
