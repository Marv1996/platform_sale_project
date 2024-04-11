CREATE TABLE IF NOT EXISTS platform_sale.users
(
    id                INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name        VARCHAR(45),
    last_name         VARCHAR(45),
    email             VARCHAR(45) UNIQUE,
    password          VARCHAR(45),
    verification_code VARCHAR(45),
    reset_token       VARCHAR(45),
    status            VARCHAR(45),
    role              VARCHAR(45)
);