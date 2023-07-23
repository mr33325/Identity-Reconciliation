CREATE DATABASE IF NOT EXISTS BiteSpeed;

USE BiteSpeed;

CREATE TABLE Contact(
    id INT AUTO_INCREMENT PRIMARY KEY,
    phoneNumber VARCHAR(255),
    email VARCHAR(255),
    linkedId INT,
    linkPrecedence ENUM('primary', 'secondary') NOT NULL,
    createdAt DATETIME,
    updatedAt DATETIME,
    deletedAt DATETIME
);