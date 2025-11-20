-- 1️⃣ Create the database
CREATE DATABASE IF NOT EXISTS womenshop;
USE womenshop;

-- 2️⃣ Table for Clothes
CREATE TABLE IF NOT EXISTS clothes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    purchase_price DOUBLE NOT NULL,
    sell_price DOUBLE NOT NULL,
    discount_price DOUBLE DEFAULT 0,
    stock INT DEFAULT 0,
    size INT NOT NULL
);

-- Insert some sample rows
INSERT INTO clothes (name, purchase_price, sell_price, size)
VALUES
('Red T-shirt', 10.0, 20.0, 36),
('Blue Jeans', 25.0, 50.0, 40),
('Black Jacket', 50.0, 100.0, 42);

-- 3️⃣ Table for Shoes
CREATE TABLE IF NOT EXISTS shoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    purchase_price DOUBLE NOT NULL,
    sell_price DOUBLE NOT NULL,
    discount_price DOUBLE DEFAULT 0,
    stock INT DEFAULT 0,
    shoe_size INT NOT NULL
);

-- Insert some sample rows
INSERT INTO shoes (name, purchase_price, sell_price, shoe_size)
VALUES
('White Sneakers', 30.0, 60.0, 38),
('Leather Boots', 60.0, 120.0, 42),
('Summer Sandals', 15.0, 30.0, 40);

-- 4️⃣ Table for Accessories
CREATE TABLE IF NOT EXISTS accessories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    purchase_price DOUBLE NOT NULL,
    sell_price DOUBLE NOT NULL,
    discount_price DOUBLE DEFAULT 0,
    stock INT DEFAULT 0
);

-- Insert some sample rows
INSERT INTO accessories (name, purchase_price, sell_price)
VALUES
('Leather Belt', 10.0, 25.0),
('Handbag', 40.0, 80.0),
('Sunglasses', 15.0, 35.0);


select * from accessories;