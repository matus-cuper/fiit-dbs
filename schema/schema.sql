CREATE TABLE statuses (
	status_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(20) NOT NULL
);

CREATE TABLE fields_of_study (
	field_of_study_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(60) NOT NULL
);

CREATE TABLE universities (
	university_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(100) NOT NULL,
	address VARCHAR(80) NOT NULL
);

CREATE TABLE secondary_schools (
	secondary_school_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(80) NOT NULL,
	address VARCHAR(80) NOT NULL
);

CREATE TABLE award_levels (
	award_level_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(30) NOT NULL
);

CREATE TABLE award_names (
	award_name_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(60) NOT NULL
);

CREATE TABLE subjects (
	subject_id SERIAL PRIMARY KEY NOT NULL,
	name VARCHAR(40) NOT NULL
);

CREATE TABLE fos_at_universities (
	fos_at_university_id SERIAL PRIMARY KEY NOT NULL,
	university_id BIGINT REFERENCES universities (university_id),
	field_of_study_id BIGINT REFERENCES fields_of_study (field_of_study_id)
);

CREATE TABLE students (
	student_id SERIAL PRIMARY KEY NOT NULL,
	secondary_school_id BIGINT REFERENCES secondary_schools (secondary_school_id),
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	birth_at DATE NOT NULL,
	address VARCHAR(80) NOT NULL,
	email VARCHAR(70) NOT NULL,
	phone CHAR(15) NOT NULL,
	zip_code CHAR(5) NOT NULL
);

CREATE TABLE graduations_from_ss (
	graduation_from_ss_id SERIAL PRIMARY KEY NOT NULL,
	student_id BIGINT REFERENCES students (student_id),
	subject_id BIGINT REFERENCES subjects (subject_id),
	graduated_at DATE NOT NULL,
	mark NUMERIC(1) NOT NULL
);

CREATE TABLE awards (
	award_id SERIAL PRIMARY KEY NOT NULL,
	award_level_id BIGINT REFERENCES award_levels (award_level_id),
	award_name_id BIGINT REFERENCES award_names (award_name_id),
	student_id BIGINT REFERENCES students (student_id),
	awarded_at DATE
);

CREATE TABLE graduations (
	graduation_id SERIAL PRIMARY KEY NOT NULL,
	field_of_study_id BIGINT REFERENCES fields_of_study (field_of_study_id),
	student_id BIGINT REFERENCES students (student_id),
	started_at DATE NOT NULL,
	finished_at DATE NOT NULL,
	graduated BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE registrations (
	registration_id SERIAL PRIMARY KEY NOT NULL,
	field_of_study_id BIGINT REFERENCES fields_of_study (field_of_study_id),
	student_id BIGINT REFERENCES students (student_id),
	status_id BIGINT REFERENCES statuses (status_id),
	changed_at DATE NOT NULL
);
