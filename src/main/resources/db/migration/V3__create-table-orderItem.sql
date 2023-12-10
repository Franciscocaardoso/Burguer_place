CREATE TABLE orderItem (
    id SERIAL PRIMARY KEY,
    qtd_itens INT NOT NULL,
    subtotal_value DOUBLE PRECISION NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id)
);
