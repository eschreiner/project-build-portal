# --- !Ups

CREATE TABLE stakeholder (
	id bigint not null,
	name text,
	CONSTRAINT stakeholder_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE s_stakeholder_id;

# --- !Downs
DROP TABLE IF EXISTS stakeholder;
DROP SEQUENCE IF EXISTS s_stakeholder_id;