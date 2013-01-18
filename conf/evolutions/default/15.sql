# --- !Ups

ALTER TABLE product 
	ADD version_id bigint;
ALTER TABLE product 
	ADD CONSTRAINT fk_product_version FOREIGN KEY (version_id)
      REFERENCES version (id);

# --- !Downs
ALTER TABLE product DROP version_id;