CREATE TABLE IF NOT EXISTS report(
    id VARCHAR(200) PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    student_registration_id VARCHAR(200) NOT NULL,
    student_name VARCHAR(200) NULL,
    payment_id VARCHAR(255) NOT NULL,
    datetime TIMESTAMP NOT NULL,
    masked_card_no VARCHAR(255) NOT NULL,
    card_type VARCHAR(25) NOT NULL
);
