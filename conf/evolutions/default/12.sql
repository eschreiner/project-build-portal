# --- !Ups

CREATE TABLE version_required
(
  id bigint not null,
  milestone_id bigint NOT NULL,
  product_id bigint NOT NULL,
  version_id bigint NOT NULL,
  deployed_id bigint,
  CONSTRAINT version_required_pkey PRIMARY KEY (id),
  CONSTRAINT fk_version_required_milestone FOREIGN KEY (milestone_id)
      REFERENCES milestone (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_version_required_product FOREIGN KEY (product_id)
      REFERENCES product (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_version_required_version FOREIGN KEY (version_id)
      REFERENCES version (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_version_required_deployed FOREIGN KEY (deployed_id)
      REFERENCES version_deployed (id)
      ON UPDATE NO ACTION ON DELETE SET NULL
);
CREATE SEQUENCE s_version_required_id;

# --- !Downs
DROP TABLE IF EXISTS version_required;
DROP SEQUENCE IF EXISTS s_version_required_id;
