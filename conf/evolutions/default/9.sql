# --- !Ups

CREATE TABLE version
(
  id bigint not null,
  name text,
  project_id bigint NOT NULL,
  tag text,
  CONSTRAINT version_pkey PRIMARY KEY (id),
  CONSTRAINT fk_version_project FOREIGN KEY (project_id)
      REFERENCES project (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_version_id;

# --- !Downs
DROP TABLE IF EXISTS version;
DROP SEQUENCE IF EXISTS s_version_id;
