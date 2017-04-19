ALTER TABLE students ALTER COLUMN secondary_school_id DROP NOT NULL;

ALTER TABLE fos_at_universities ADD UNIQUE (university_id, field_of_study_id);
