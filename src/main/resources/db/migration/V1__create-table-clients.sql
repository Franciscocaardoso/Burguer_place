CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    postal_code VARCHAR(8) NOT NULL,
    residential_number INT,
    complement VARCHAR(255)
);
