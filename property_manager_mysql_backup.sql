-- MySQL Database Backup
-- Generated on: Mon Jul 21 17:11:05 BDT 2025

-- Buildings Table
DELETE FROM assets;
DELETE FROM rooms;
DELETE FROM buildings;
INSERT INTO buildings (id, name) VALUES (1, 'Main Building');
INSERT INTO buildings (id, name) VALUES (2, 'Warehouse A');
INSERT INTO buildings (id, name) VALUES (3, 'Office Complex B');
INSERT INTO buildings (id, name) VALUES (4, 'Laboratory Wing');
INSERT INTO buildings (id, name) VALUES (5, 'Storage Facility C');
INSERT INTO buildings (id, name) VALUES (6, 'Main Building');
INSERT INTO buildings (id, name) VALUES (7, 'Warehouse A');
INSERT INTO buildings (id, name) VALUES (8, 'Office Complex B');
INSERT INTO buildings (id, name) VALUES (9, 'Laboratory Wing');
INSERT INTO buildings (id, name) VALUES (10, 'Storage Facility C');
INSERT INTO buildings (id, name) VALUES (11, 'i love you');

-- Rooms Table
INSERT INTO rooms (id, name, type, building_id, available) VALUES (1, 'Conference Room 1', 'Meeting Room', 1, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (2, 'Storage Room 1', 'Storage', 2, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (3, 'Office 101', 'Office', 3, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (4, 'Reception Area', 'Reception', 1, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (5, 'Lab Room A', 'Lab', 4, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (6, 'Lab Room B', 'Lab', 4, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (7, 'Classroom 201', 'Classroom', 1, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (8, 'Storage Room 2', 'Storage', 5, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (9, 'Conference Room 1', 'Meeting Room', 1, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (10, 'Storage Room 1', 'Storage', 2, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (11, 'Office 101', 'Office', 3, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (12, 'Reception Area', 'Reception', 1, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (13, 'Lab Room A', 'Lab', 4, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (14, 'Lab Room B', 'Lab', 4, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (15, 'Classroom 201', 'Classroom', 1, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (16, 'Storage Room 2', 'Storage', 5, true);
INSERT INTO rooms (id, name, type, building_id, available) VALUES (17, 'i wanna see you', 'Office', 1, true);

-- Assets Table
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (1, 'Projector XYZ', 'Electronics', 1, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (2, 'Storage Rack #1', 'Furniture', 2, 'Excellent');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (3, 'Desktop Computer', 'Electronics', 3, 'Fair');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (4, 'Reception Desk', 'Furniture', 4, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (5, 'Microscope Model A', 'Electronics', 5, 'Excellent');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (6, 'Lab Table Set', 'Furniture', 5, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (7, 'Whiteboard', 'Furniture', 7, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (8, 'Air Conditioning Unit', 'AC Unit', 1, 'Under Maintenance');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (9, 'Office Chair Set', 'Chair', 3, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (10, 'Lab Equipment Cabinet', 'Furniture', 6, 'Excellent');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (11, 'Network Switch', 'Electronics', 3, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (12, 'Fire Extinguisher', 'Safety', 2, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (13, 'Projector XYZ', 'Electronics', 1, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (14, 'Storage Rack #1', 'Furniture', 2, 'Excellent');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (15, 'Desktop Computer', 'Electronics', 3, 'Fair');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (16, 'Reception Desk', 'Furniture', 4, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (17, 'Microscope Model A', 'Electronics', 5, 'Excellent');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (18, 'Lab Table Set', 'Furniture', 5, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (19, 'Whiteboard', 'Furniture', 7, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (20, 'Air Conditioning Unit', 'AC Unit', 1, 'Under Maintenance');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (21, 'Office Chair Set', 'Chair', 3, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (22, 'Lab Equipment Cabinet', 'Furniture', 6, 'Excellent');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (23, 'Network Switch', 'Electronics', 3, 'Good');
INSERT INTO assets (id, name, type, room_id, condition_status) VALUES (24, 'Fire Extinguisher', 'Safety', 2, 'Good');
