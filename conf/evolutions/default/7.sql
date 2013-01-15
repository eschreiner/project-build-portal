# --- !Ups

CREATE TABLE product_used
(
  product_id bigint NOT NULL,
  project_id bigint NOT NULL,
  CONSTRAINT product_used_pkey PRIMARY KEY (product_id,project_id),
  CONSTRAINT fk_product_used_product FOREIGN KEY (product_id)
      REFERENCES product (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_product_used_project FOREIGN KEY (project_id)
      REFERENCES project (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);

# --- !Downs
DROP TABLE IF EXISTS product_used;
