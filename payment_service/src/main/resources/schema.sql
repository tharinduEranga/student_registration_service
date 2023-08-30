CREATE TABLE IF NOT EXISTS payment(
    id VARCHAR(20) PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    student_registration_id VARCHAR(20) NOT NULL,
    payment_id_from_gateway VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    metadata TEXT,
    datetime TIMESTAMP NOT NULL
);
