# --- !Ups

CREATE TABLE project (
	id bigint not null,
	name text,
	dormant boolean,
	CONSTRAINT project_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE s_project_id;

# --- !Downs
DROP TABLE IF EXISTS project;
DROP SEQUENCE IF EXISTS s_project_id;