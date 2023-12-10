CREATE TABLE order_item (
    id              SERIAL              PRIMARY KEY,
    qtd_itens       INT                 NOT NULL,
    subtotal_value  DOUBLE PRECISION    NOT NULL,
    product_id      BIGINT              NOT NULL,
    order_id        BIGINT              NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (order_id)   REFERENCES orders (id)
);
