# --- !Ups

ALTER TABLE stakeholder 
	ADD email text;
ALTER TABLE stakeholder 
	ADD salt text;
ALTER TABLE stakeholder 
	ADD password text;

# --- !Downs
ALTER TABLE stakeholder DROP email;
ALTER TABLE stakeholder DROP salt;
ALTER TABLE stakeholder DROP password;