# --- !Ups

CREATE TABLE build
(
  id bigint not null,
  name text,
  version_id bigint NOT NULL,
  built timestamp NOT NULL,
  CONSTRAINT build_pkey PRIMARY KEY (id),
  CONSTRAINT fk_build_version FOREIGN KEY (version_id)
      REFERENCES version (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_build_id;

# --- !Downs
DROP TABLE IF EXISTS build;
DROP SEQUENCE IF EXISTS s_build_id;
