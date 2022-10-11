CREATE DATABASE ordersApp;

USE ordersApp;

CREATE TABLE users (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL,
password VARCHAR(255) NOT NULL
);

CREATE TABLE order_types (
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
name VARCHAR(100),
FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders (
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
order_type_id INT NOT NULL DEFAULT 0,
price INT NOT NULL,
title VARCHAR(50) NOT NULL,
quantity INT NOT NULL,
plannedCompletionDate DATE NOT NULL,
completed BIT NOT NULL DEFAULT 0,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (order_type_id) REFERENCES order_types(id)
);