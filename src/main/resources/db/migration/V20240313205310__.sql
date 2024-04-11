CREATE TABLE IF NOT EXISTS platform_sale.car_statements
(
    car_statements_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    car_name          VARCHAR(45),
    model             VARCHAR(45),
    year              VARCHAR(45),
    color             VARCHAR(45),
    statement_id      VARCHAR(45),
    user_id           VARCHAR(45)
);