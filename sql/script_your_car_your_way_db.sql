-- Database schema for car reservation system

CREATE DATABASE IF NOT EXISTS car_reservation_db 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE car_reservation_db;

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    gender VARCHAR(10),
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address TEXT,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login_at TIMESTAMP,
    account_status VARCHAR(20) DEFAULT 'active'
);

CREATE TABLE Client (
    id INT PRIMARY KEY,
    email_contact_cache BOOLEAN DEFAULT false,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE Operator (
    id INT PRIMARY KEY,
    role VARCHAR(100),
    availability BOOLEAN DEFAULT 1,
    FOREIGN KEY (id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE Agency (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    city VARCHAR(150) NOT NULL,
    address TEXT NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Car (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(10) NOT NULL,
    model VARCHAR(100) NOT NULL,
    hourly_rental_rate DECIMAL(10, 2) NOT NULL,
    fiscal_power INT NOT NULL,
    availability BOOLEAN DEFAULT 1,
    agency_id INT,
    mileage INT,
    FOREIGN KEY (agency_id) REFERENCES Agency(id) ON DELETE SET NULL
);

CREATE TABLE Reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    operator_id INT,
    car_id INT NOT NULL,
    departure_city VARCHAR(100) NOT NULL,
    return_city VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    return_datetime TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    payment_date TIMESTAMP,
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE,
    FOREIGN KEY (operator_id) REFERENCES Operator(id) ON DELETE SET NULL,
    FOREIGN KEY (car_id) REFERENCES Car(id) ON DELETE RESTRICT
);

CREATE TABLE Ticket(
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    operator_id INT,
    reservation_id INT,
    subject VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'open',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    resolved_at TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE,
    FOREIGN KEY (operator_id) REFERENCES Operator(id) ON DELETE SET NULL,
    FOREIGN KEY (reservation_id) REFERENCES Reservation(id) ON DELETE SET NULL
);

CREATE TABLE Message (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT,
    client_id INT,
    operator_id INT,
    ticket_id INT,
    title VARCHAR(255),
    message_text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    attachment VARCHAR(500),
    FOREIGN KEY (reservation_id) REFERENCES Reservation(id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES Client(id) ON DELETE CASCADE,
    FOREIGN KEY (operator_id) REFERENCES Operator(id) ON DELETE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES Ticket(id) ON DELETE CASCADE
);

CREATE TABLE Payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT,
    amount DECIMAL(10, 2)NOT NULL,
    currency VARCHAR(3) DEFAULT 'EUR',
    status VARCHAR(20) DEFAULT 'pending',
    payment_method VARCHAR(50),
    payment_date TIMESTAMP,
    stripe_transaction_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES Reservation(id) ON DELETE CASCADE
);

ALTER TABLE Reservation ADD CONSTRAINT check_dates 
    CHECK (return_datetime > created_at);
ALTER TABLE Car ADD CONSTRAINT check_rate 
    CHECK (hourly_rental_rate > 0);
ALTER TABLE Payment ADD CONSTRAINT check_amount 
    CHECK (stripe_transaction_id IS NOT NULL OR status = 'pending');