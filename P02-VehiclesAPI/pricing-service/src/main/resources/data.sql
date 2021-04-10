DROP TABLE IF EXISTS price;

CREATE TABLE price (
    vehicleId INT AUTO_INCREMENT PRIMARY KEY,
    currency VARCHAR(20) NOT NULL,
    price INT NOT NULL
);

INSERT INTO price (vehicleId, currency, price) VALUES (1, 'USD', 15000);
INSERT INTO price (vehicleId, currency, price) VALUES (2, 'USD', 23000);
INSERT INTO price (vehicleId, currency, price) VALUES (3, 'USD', 31000);
INSERT INTO price (vehicleId, currency, price) VALUES (4, 'USD', 17000);
INSERT INTO price (vehicleId, currency, price) VALUES (5, 'USD', 28000);