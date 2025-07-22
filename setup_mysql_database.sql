-- Property Asset Manager Database Setup for MySQL
-- Run this script in phpMyAdmin or MySQL command line

-- Create the database
CREATE DATABASE IF NOT EXISTS property_asset_manager 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE property_asset_manager;

-- Create buildings table
CREATE TABLE IF NOT EXISTS buildings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_building_name (name)
);

-- Create rooms table
CREATE TABLE IF NOT EXISTS rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100),
    building_id INT,
    available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (building_id) REFERENCES buildings(id) ON DELETE CASCADE,
    INDEX idx_room_name (name),
    INDEX idx_room_building (building_id)
);

-- Create assets table
CREATE TABLE IF NOT EXISTS assets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100),
    room_id INT,
    condition_status VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    INDEX idx_asset_name (name),
    INDEX idx_asset_room (room_id)
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') NOT NULL DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
);

-- Insert default admin user and test user
INSERT INTO users (full_name, username, password, role) 
VALUES ('System Administrator', 'admin', 'admin123', 'admin')
ON DUPLICATE KEY UPDATE full_name = full_name;

INSERT INTO users (full_name, username, password, role) 
VALUES ('John Doe', 'user', 'user123', 'user')
ON DUPLICATE KEY UPDATE full_name = full_name;

-- Insert sample data for testing
INSERT INTO buildings (name) VALUES 
('Main Building'),
('Warehouse A'),
('Office Complex B'),
('Laboratory Wing'),
('Storage Facility C')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rooms (name, type, building_id, available) VALUES 
('Conference Room 1', 'Meeting Room', 1, TRUE),
('Storage Room 1', 'Storage', 2, TRUE),
('Office 101', 'Office', 3, TRUE),
('Reception Area', 'Reception', 1, TRUE),
('Lab Room A', 'Lab', 4, TRUE),
('Lab Room B', 'Lab', 4, TRUE),
('Classroom 201', 'Classroom', 1, TRUE),
('Storage Room 2', 'Storage', 5, TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO assets (name, type, room_id, condition_status) VALUES 
('Projector XYZ', 'Electronics', 1, 'Good'),
('Storage Rack #1', 'Furniture', 2, 'Excellent'),
('Desktop Computer', 'Electronics', 3, 'Fair'),
('Reception Desk', 'Furniture', 4, 'Good'),
('Microscope Model A', 'Electronics', 5, 'Excellent'),
('Lab Table Set', 'Furniture', 5, 'Good'),
('Whiteboard', 'Furniture', 7, 'Good'),
('Air Conditioning Unit', 'AC Unit', 1, 'Under Maintenance'),
('Office Chair Set', 'Chair', 3, 'Good'),
('Lab Equipment Cabinet', 'Furniture', 6, 'Excellent'),
('Network Switch', 'Electronics', 3, 'Good'),
('Fire Extinguisher', 'Safety', 2, 'Good')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Display success message
SELECT 'Database setup completed successfully!' AS status;
