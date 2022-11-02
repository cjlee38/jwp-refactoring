ALTER TABLE menu_product
    ADD COLUMN price DECIMAL(19, 2);

-- UPDATE menu_product mp
--     INNER JOIN product p ON mp.product_id = p.id
-- SET mp.price = mp.quantity * p.price
-- WHERE mp.price IS NULL;

CREATE TABLE order_table_ref
(
    order_table_id BIGINT(20) NOT NULL,
    table_group_id BIGINT(20) NOT NULL
);


ALTER TABLE order_table_ref
    ADD CONSTRAINT fk_order_table_ref_order_table
        FOREIGN KEY (order_table_id) REFERENCES order_table (id);