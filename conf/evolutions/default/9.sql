# --- !Ups

CREATE TABLE version
(
  id bigint not null,
  name text,
  product_id bigint NOT NULL,
  tag text,
  CONSTRAINT version_pkey PRIMARY KEY (id),
  CONSTRAINT fk_version_product FOREIGN KEY (product_id)
      REFERENCES product (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_version_id;

# --- !Downs
DROP TABLE IF EXISTS version;
DROP SEQUENCE IF EXISTS s_version_id;
