# --- !Ups

CREATE TABLE version_deployed
(
  id bigint not null,
  version_id bigint NOT NULL,
  build_id bigint NOT NULL,
  CONSTRAINT version_deployed_pkey PRIMARY KEY (id),
  CONSTRAINT fk_version_deployed_version FOREIGN KEY (version_id)
      REFERENCES version (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_version_deployed_build FOREIGN KEY (build_id)
      REFERENCES build (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_version_deployed_id;

# --- !Downs
DROP TABLE IF EXISTS version_deployed;
DROP SEQUENCE IF EXISTS s_version_deployed_id;
