CREATE TABLE customers (
    id                  SERIAL          PRIMARY KEY,
    name                VARCHAR(255)    NOT NULL,
    email               VARCHAR(255)    NOT NULL UNIQUE,
    cpf                 VARCHAR(11)     NOT NULL UNIQUE,
    postal_code         VARCHAR(8)      NOT NULL,
    residential_number  INT,
    active              BOOLEAN         NOT NULL,
    complement          VARCHAR(255)
);
