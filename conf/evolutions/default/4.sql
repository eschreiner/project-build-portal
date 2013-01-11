# --- !Ups

ALTER TABLE project 
	ADD owner_id bigint;
ALTER TABLE project 
	ADD CONSTRAINT fk_project_owner FOREIGN KEY (owner_id)
      REFERENCES stakeholder (id);

# --- !Downs
ALTER TABLE project DROP owner_id;