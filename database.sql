CREATE DATABASE IF NOT EXISTS learning_ewallet;

USE learning_ewallet;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS wallets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    version BIGINT,

    CONSTRAINT fk_wallet_user
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS transfers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reference BINARY(16) NOT NULL UNIQUE,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    created_at DATETIME(6) NOT NULL,

    CONSTRAINT fk_transfer_sender
    FOREIGN KEY (sender_id) REFERENCES users(id),

    CONSTRAINT fk_transfer_receiver
    FOREIGN KEY (receiver_id) REFERENCES users(id),

    CONSTRAINT chk_transfer_amount CHECK (amount > 0)
);

INSERT INTO users (name)
VALUES
    ('Ana'),
    ('Budi'),
    ('Cici');

INSERT INTO wallets (user_id, balance, version)
VALUES
    (1, 1000.00, 0),
    (2, 1000.00, 0),
    (3, 1000.00, 0);