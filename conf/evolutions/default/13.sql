# --- !Ups

CREATE TABLE project_stakeholder
(
  id bigint not null,
  stakeholder_id bigint NOT NULL,
  project_id bigint NOT NULL,
  CONSTRAINT project_stakeholder_pkey PRIMARY KEY (id),
  CONSTRAINT fk_project_stakeholder_stakeholder FOREIGN KEY (stakeholder_id)
      REFERENCES stakeholder (id)
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_project_stakeholder_project FOREIGN KEY (project_id)
      REFERENCES project (id)
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE s_project_stakeholder_id;

# --- !Downs
DROP TABLE IF EXISTS project_stakeholder;
DROP SEQUENCE IF EXISTS s_project_stakeholder_id;
