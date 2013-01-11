# --- !Ups

CREATE TABLE product (
	id bigint not null,
	name text,
	CONSTRAINT product_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE s_product_id;

# --- !Downs
DROP TABLE IF EXISTS product;
DROP SEQUENCE IF EXISTS s_product_id;