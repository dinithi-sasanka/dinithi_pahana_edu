-- Update bills table to include bill number and bill date time
ALTER TABLE bills ADD COLUMN bill_number VARCHAR(50) AFTER customer_id;
ALTER TABLE bills ADD COLUMN bill_date_time VARCHAR(100) AFTER bill_date;

-- Add unique constraint on bill_number to ensure uniqueness
ALTER TABLE bills ADD UNIQUE (bill_number); 