-- 1. Create the database
CREATE DATABASE pahana_edu;

-- 2. Select the database
USE pahana_edu;

-- 3. Create the users table
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       use_name VARCHAR(100),
                       username VARCHAR(50),
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20),
                       email VARCHAR(100),
                       telephone VARCHAR(20)
);


-- Insert an Admin user
INSERT INTO users (use_name, username, password, role, email, telephone)
VALUES ('Dinithi Sasanka', 'dinithi_admin', 'admin123', 'admin', 'admin@example.com', '0771234567');

-- Insert a Co-Admin user
INSERT INTO users (use_name, username, password, role, email, telephone)
VALUES ('Kasun Perera', 'kasun_coadmin', 'coadmin123', 'coadmin', 'coadmin@example.com', '0772345678');

-- Insert a Staff user
INSERT INTO users (use_name, username, password, role, email, telephone)
VALUES ('Nimali Silva', 'nimali_staff', 'staff123', 'staff', 'staff@example.com', '0773456789');

-- Insert another Staff user
INSERT INTO users (use_name, username, password, role, email, telephone)
VALUES ('Tharindu Weerasinghe', 'tharindu_staff', 'staff456', 'staff', 'staff2@example.com', '0774567890');


-- Create the customers table

USE pahana_edu;
CREATE TABLE customers (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           account_number VARCHAR(20),
                           name VARCHAR(100),
                           address TEXT,
                           telephone VARCHAR(20),
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           email VARCHAR(255)
);


-- Insert Customer 1
INSERT INTO customers (account_number, name, address, telephone, email)
VALUES ('AC1001', 'Nimal Perera', '123 Galle Road, Colombo', '0771234567', 'nimal@example.com');

-- Insert Customer 2
INSERT INTO customers (account_number, name, address, telephone, email)
VALUES ('AC1002', 'Kamal Silva', '45 Kandy Road, Kandy', '0772345678', 'kamal@example.com');

-- Insert Customer 3
INSERT INTO customers (account_number, name, address, telephone, email)
VALUES ('AC1003', 'Sunethra Fernando', '78 Beach Road, Negombo', '0773456789', 'sunethra@example.com');

-- Insert Customer 4
INSERT INTO customers (account_number, name, address, telephone, email)
VALUES ('AC1004', 'Chathura Jayasinghe', '22 Temple Lane, Galle', '0774567890', 'chathura@example.com');

-- Insert Customer 5
INSERT INTO customers (account_number, name, address, telephone, email)
VALUES ('AC1005', 'Dilani Weerakoon', '10 Hill Street, Matara', '0775678901', 'dilani@example.com');


-- Create the items table
USE pahana_edu;
CREATE TABLE items (
                       id INT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       category VARCHAR(100) NOT NULL,
                       description TEXT DEFAULT NULL,
                       price DECIMAL(10,2) NOT NULL,
                       stock INT DEFAULT NULL,
                       PRIMARY KEY (id)
);


INSERT INTO items (name, category, description, price, stock)
VALUES
    ('Laptop', 'Electronics', '15-inch display, 8GB RAM, 256GB SSD', 850.00, 10),
    ('Coffee Mug', 'Kitchen', 'Ceramic mug, 350ml capacity', 5.50, 50),
    ('Office Chair', 'Furniture', 'Ergonomic design with adjustable height', 120.00, 15),
    ('Headphones', 'Electronics', 'Wireless noise-cancelling headphones', 199.99, 25),
    ('Notebook', 'Stationery', '200-page spiral notebook', 2.50, 100);



-- Create the stock table
USE pahana_edu;
CREATE TABLE stock (
                       id INT NOT NULL AUTO_INCREMENT,
                       item_id INT NOT NULL,
                       current_stock INT NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (item_id) REFERENCES items(id)
);


-- Assuming these item IDs exist in your items table
INSERT INTO stock (item_id, current_stock) VALUES
                                               (1, 10),   -- Laptop
                                               (2, 50),   -- Coffee Mug
                                               (3, 15),   -- Office Chair
                                               (4, 25),   -- Headphones
                                               (5, 100);  -- Notebook


-- Create the bills table
USE pahana_edu;
CREATE TABLE bills (
                       id INT NOT NULL AUTO_INCREMENT,
                       customer_id INT NOT NULL,
                       bill_number VARCHAR(50) NOT NULL,
                       bill_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       bill_date_time VARCHAR(100) DEFAULT NULL,
                       total_amount DECIMAL(10,2) NOT NULL,
                       paid_amount DECIMAL(10,2) DEFAULT '0.00',
                       balance DECIMAL(10,2) DEFAULT '0.00',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       PRIMARY KEY (id),
                       FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Bill 1
INSERT INTO bills (
    customer_id,
    bill_number,
    bill_date,
    bill_date_time,
    total_amount,
    paid_amount,
    balance
) VALUES (
             1,
             'BILL-2025-001',
             NOW(),
             '2025-08-10 14:30:00',
             150.00,
             100.00,
             50.00
         );

-- Bill 2
INSERT INTO bills (
    customer_id,
    bill_number,
    bill_date,
    bill_date_time,
    total_amount,
    paid_amount,
    balance
) VALUES (
             2,
             'BILL-2025-002',
             NOW(),
             '2025-08-10 15:45:00',
             320.00,
             320.00,
             0.00
         );

-- Bill 3
INSERT INTO bills (
    customer_id,
    bill_number,
    bill_date,
    bill_date_time,
    total_amount,
    paid_amount,
    balance
) VALUES (
             1,
             'BILL-2025-003',
             NOW(),
             '2025-08-09 11:10:00',
             85.00,
             50.00,
             35.00
         );

-- Bill 4
INSERT INTO bills (
    customer_id,
    bill_number,
    bill_date,
    bill_date_time,
    total_amount,
    paid_amount,
    balance
) VALUES (
             3,
             'BILL-2025-004',
             NOW(),
             '2025-08-08 18:20:00',
             600.00,
             400.00,
             200.00
         );

-- Bill 5
INSERT INTO bills (
    customer_id,
    bill_number,
    bill_date,
    bill_date_time,
    total_amount,
    paid_amount,
    balance
) VALUES (
             4,
             'BILL-2025-005',
             NOW(),
             '2025-08-07 09:00:00',
             250.00,
             250.00,
             0.00
         );


-- Create the bill_items table
USE pahana_edu;
CREATE TABLE bill_items (
                            id INT NOT NULL AUTO_INCREMENT,
                            bill_id INT NOT NULL,
                            item_id INT NOT NULL,
                            quantity INT NOT NULL,
                            price DECIMAL(10,2) NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (id),
                            FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE,
                            FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE
);

-- Bill 1 items
INSERT INTO bill_items (bill_id, item_id, quantity, price)
VALUES
    (1, 1, 2, 50.00),  -- 2 units of item 1 @ 50.00 each
    (1, 2, 1, 50.00);  -- 1 unit of item 2 @ 50.00 each

-- Bill 2 items
INSERT INTO bill_items (bill_id, item_id, quantity, price)
VALUES
    (2, 3, 4, 80.00),  -- 4 units of item 3 @ 80.00 each
    (2, 4, 2, 80.00);  -- 2 units of item 4 @ 80.00 each

-- Bill 3 items
INSERT INTO bill_items (bill_id, item_id, quantity, price)
VALUES
    (3, 1, 1, 35.00),  -- 1 unit of item 1 @ 35.00 each
    (3, 5, 1, 50.00);  -- 1 unit of item 5 @ 50.00 each

-- Bill 4 items
INSERT INTO bill_items (bill_id, item_id, quantity, price)
VALUES
    (4, 2, 5, 80.00),  -- 5 units of item 2 @ 80.00 each
    (4, 3, 2, 100.00); -- 2 units of item 3 @ 100.00 each

-- Bill 5 items
INSERT INTO bill_items (bill_id, item_id, quantity, price)
VALUES
    (5, 4, 1, 150.00), -- 1 unit of item 4 @ 150.00 each
    (5, 5, 2, 50.00);  -- 2 units of item 5 @ 50.00 each



-- ========================================
-- TEST QUERIES
-- ========================================

-- 1. View all bills with customer details
SELECT
    b.id AS bill_id,
    b.bill_number,
    c.name AS customer_name,
    b.total_amount,
    b.paid_amount,
    b.balance,
    b.bill_date_time
FROM bills b
         JOIN customers c ON b.customer_id = c.id;

-- 2. View each bill with its items and item details
SELECT
    b.bill_number,
    c.name AS customer_name,
    i.name AS item_name,
    bi.quantity,
    bi.price,
    (bi.quantity * bi.price) AS line_total
FROM bill_items bi
         JOIN bills b ON bi.bill_id = b.id
         JOIN customers c ON b.customer_id = c.id
         JOIN items i ON bi.item_id = i.id
ORDER BY b.bill_number;

-- 3. Check all bill_items for a specific bill
SELECT * FROM bill_items WHERE bill_id = 1;

-- ========================================
-- TEST CASCADE DELETE
-- ========================================
-- This will delete Bill ID 1 and all its related bill_items automatically
DELETE FROM bills WHERE id = 1;

-- After delete, check that bill_items with bill_id = 1 are gone
SELECT * FROM bill_items WHERE bill_id = 1;
