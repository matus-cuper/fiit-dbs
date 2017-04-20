CREATE INDEX ON graduations_from_ss (student_id);
CREATE INDEX ON awards (student_id);
CREATE INDEX ON registrations (student_id);
CREATE INDEX ON graduations (student_id);

CREATE INDEX ON students (name);
CREATE INDEX ON students (surname);
CREATE INDEX ON students (birth_at);

CREATE INDEX ON main_table_1 (name);
CREATE INDEX ON main_table_1 (surname);
CREATE INDEX ON main_table_1 (birth_at);
CREATE INDEX ON main_table_1 (gss_avg);
CREATE INDEX ON main_table_1 (r_count);
