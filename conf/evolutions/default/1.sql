# --- !Ups

CREATE TABLE project (
	id bigint not null,
	name text,
	active boolean,
	CONSTRAINT project_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE s_project_id;

# --- !Downs
DROP TABLE IF EXISTS project;
DROP SEQUENCE IF EXISTS s_project_id;