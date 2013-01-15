# --- !Ups

CREATE TABLE product_used
(
  id bigint not null,
  product_id bigint NOT NULL,
  project_id bigint NOT NULL,
  CONSTRAINT product_used_pkey PRIMARY KEY (id),
  CONSTRAINT fk_product_used_product FOREIGN KEY (product_id)
      REFERENCES product (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_product_used_project FOREIGN KEY (project_id)
      REFERENCES project (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_product_used_id;

# --- !Downs
DROP TABLE IF EXISTS product_used;
DROP SEQUENCE IF EXISTS s_product_used_id;
