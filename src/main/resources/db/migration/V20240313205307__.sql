CREATE TABLE IF NOT EXISTS platform_sale.statements
(
    statement_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title        VARCHAR(45),
    description  VARCHAR(45),
    price        VARCHAR(45),
    type         VARCHAR(45),
    location     VARCHAR(45)
);