-- Add paid_amount and balance columns to bills table
ALTER TABLE bills ADD COLUMN paid_amount DECIMAL(10,2) DEFAULT 0.00 AFTER total_amount;
ALTER TABLE bills ADD COLUMN balance DECIMAL(10,2) DEFAULT 0.00 AFTER paid_amount; 