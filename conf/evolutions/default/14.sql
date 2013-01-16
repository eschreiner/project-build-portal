# --- !Ups

ALTER TABLE project 
	ADD milestone_id bigint;
ALTER TABLE project 
	ADD CONSTRAINT fk_project_milestone FOREIGN KEY (milestone_id)
      REFERENCES milestone (id);

# --- !Downs
ALTER TABLE project DROP milestone_id;