@echo off
echo Property Asset Manager - Database Setup and Run Script
echo ======================================================

echo.
echo Step 1: Setting up MySQL Database...
echo Please make sure XAMPP is running with MySQL service started.
echo.

echo Step 2: Creating Database...
echo Copy the following SQL commands to phpMyAdmin (http://localhost/phpmyadmin/):
echo.
echo CREATE DATABASE IF NOT EXISTS property_asset_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
echo USE property_asset_manager;
echo.
echo Then import the file: setup_mysql_database.sql
echo.

echo Step 3: Configure Database...
echo After importing the SQL file, you can:
echo 1. Use SQLite (default) - no additional setup needed
echo 2. Use MySQL - run DatabaseSwitcher.java to configure
echo.

echo Step 4: Running the Application...
echo Please open this project in NetBeans IDE and run the Main.java file.
echo.
echo Default Login Credentials:
echo Username: admin
echo Password: admin123
echo.

echo Note: This is a JavaFX application that requires an IDE with JavaFX support
echo or proper JavaFX runtime configuration.
echo.

pause
