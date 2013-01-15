# --- !Ups

CREATE TABLE milestone
(
  id bigint not null,
  name text,
  project_id bigint NOT NULL,
  deadline date,
  active boolean,
  CONSTRAINT milestone_pkey PRIMARY KEY (id),
  CONSTRAINT fk_milestone_project FOREIGN KEY (project_id)
      REFERENCES project (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_milestone_id;

# --- !Downs
DROP TABLE IF EXISTS milestone;
DROP SEQUENCE IF EXISTS s_milestone_id;
