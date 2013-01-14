# --- !Ups

CREATE TABLE token (
	id bigint not null,
	series text,
	user_id text,
	token text,
	CONSTRAINT token_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE s_token_id;

# --- !Downs
DROP TABLE IF EXISTS token;
DROP SEQUENCE IF EXISTS s_token_id;