SELECT COUNT(*) FROM statuses;
SELECT COUNT(*) FROM fields_of_study;
SELECT COUNT(*) FROM award_levels;
SELECT COUNT(*) FROM award_names;
SELECT COUNT(*) FROM subjects;
SELECT COUNT(*) FROM universities;
SELECT COUNT(*) FROM secondary_schools;
SELECT COUNT(*) FROM fos_at_universities;
SELECT COUNT(*) FROM students;
SELECT COUNT(*) FROM graduations_from_ss;
SELECT COUNT(*) FROM awards;
SELECT COUNT(*) FROM graduations;
SELECT COUNT(*) FROM registrations;

SELECT s.name, COUNT(r.registration_id)
FROM statuses s
JOIN registrations r ON s.status_id = r.status_id
GROUP BY s.status_id
ORDER BY count DESC;

SELECT nt.count, COUNT(nt.count) FROM
	(
	SELECT fos.name, COUNT(fu.field_of_study_id)
	FROM fos_at_universities fu
	JOIN fields_of_study fos ON fu.field_of_study_id = fos.field_of_study_id
	GROUP BY fos.field_of_study_id
	ORDER BY count DESC
	) AS nt
GROUP BY nt.count
ORDER BY nt.count;

SELECT al.name, COUNT(a.award_id)
FROM award_levels al
JOIN awards a ON al.award_level_id = a.award_level_id
GROUP BY al.award_level_id
ORDER BY count DESC;

SELECT an.name, COUNT(a.award_id)
FROM award_names an
JOIN awards a ON an.award_name_id = a.award_name_id
GROUP BY an.award_name_id
ORDER BY count DESC
LIMIT 10;

SELECT s.name, COUNT(g.subject_id)
FROM subjects s
JOIN graduations_from_ss g ON s.subject_id = g.subject_id
GROUP BY s.subject_id
ORDER BY count DESC
LIMIT 10;

SELECT u.name, COUNT(f.university_id)
FROM universities u
JOIN fos_at_universities f ON f.university_id = u.university_id
GROUP BY u.university_id
ORDER BY count DESC
LIMIT 10;

SELECT ss.name, COUNT(s.secondary_school_id)
FROM secondary_schools ss
JOIN students s ON ss.secondary_school_id = s.secondary_school_id
GROUP BY ss.secondary_school_id
ORDER BY count DESC
LIMIT 10;

SELECT u.name, COUNT(nt.student_id)
FROM universities u
JOIN
	(
	SELECT DISTINCT r.student_id, f.university_id
	FROM registrations r
	JOIN fos_at_universities f ON r.fos_at_university_id = f.fos_at_university_id
	) AS nt ON u.university_id = nt.university_id
GROUP BY u.university_id
ORDER BY count DESC
LIMIT 10;

SELECT f.name, COUNT(nt.student_id)
FROM fields_of_study f
JOIN
	(
	SELECT DISTINCT g.student_id, fu.field_of_study_id
	FROM graduations g
	JOIN fos_at_universities fu ON g.fos_at_university_id = fu.fos_at_university_id
	WHERE g.graduated = FALSE
	) AS nt ON f.field_of_study_id = nt.field_of_study_id
GROUP BY f.field_of_study_id
ORDER BY count DESC
LIMIT 10;

SELECT g.mark, COUNT(g.mark)
ORDER BY g.mark;
FROM graduations_from_ss g
GROUP BY g.mark

SELECT s.name, COUNT(s.student_id)
FROM students s
GROUP BY s.name
ORDER BY count DESC
LIMIT 10;

SELECT s.surname, COUNT(s.student_id)
FROM students s
GROUP BY s.surname
ORDER BY count DESC
LIMIT 10;

SELECT SUBSTR(s.email, POSITION('@' in s.email) + 1, LENGTH(s.email)), COUNT(s.student_id)
FROM students s
GROUP BY SUBSTR(s.email, POSITION('@' in s.email) + 1, LENGTH(s.email))
ORDER BY count DESC;

SELECT MIN(EXTRACT(YEAR FROM birth_at)),
MAX(EXTRACT(YEAR FROM birth_at)),
AVG(EXTRACT(YEAR FROM birth_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM birth_at))
FROM students;

SELECT MIN(EXTRACT(YEAR FROM graduated_at)),
MAX(EXTRACT(YEAR FROM graduated_at)),
AVG(EXTRACT(YEAR FROM graduated_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM graduated_at))
FROM graduations_from_ss;

SELECT MIN(mark),
MAX(mark),
AVG(mark),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY mark)
FROM graduations_from_ss;

SELECT MIN(EXTRACT(YEAR FROM awarded_at)),
MAX(EXTRACT(YEAR FROM awarded_at)),
AVG(EXTRACT(YEAR FROM awarded_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM awarded_at))
FROM awards;

SELECT MIN(EXTRACT(YEAR FROM started_at)),
MAX(EXTRACT(YEAR FROM started_at)),
AVG(EXTRACT(YEAR FROM started_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM started_at))
FROM graduations;

SELECT MIN(EXTRACT(YEAR FROM finished_at)),
MAX(EXTRACT(YEAR FROM finished_at)),
AVG(EXTRACT(YEAR FROM finished_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM finished_at))
FROM graduations;

SELECT MIN(EXTRACT(YEAR FROM changed_at)),
MAX(EXTRACT(YEAR FROM changed_at)),
AVG(EXTRACT(YEAR FROM changed_at)),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY EXTRACT(YEAR FROM changed_at))
FROM registrations;

SELECT MIN(award_level_id), MAX(award_level_id), AVG(award_level_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY award_level_id)
FROM awards;

SELECT MIN(award_name_id), MAX(award_name_id), AVG(award_name_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY award_name_id)
FROM awards;

SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM awards;

SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM graduations_from_ss;

SELECT MIN(subject_id), MAX(subject_id), AVG(subject_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY subject_id)
FROM graduations_from_ss;

SELECT MIN(secondary_school_id), MAX(secondary_school_id), AVG(secondary_school_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY secondary_school_id)
FROM students;

SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM graduations;

SELECT MIN(fos_at_university_id), MAX(fos_at_university_id), AVG(fos_at_university_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY fos_at_university_id)
FROM graduations;

SELECT MIN(student_id), MAX(student_id), AVG(student_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY student_id)
FROM registrations;

SELECT MIN(fos_at_university_id), MAX(fos_at_university_id), AVG(fos_at_university_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY fos_at_university_id)
FROM registrations;

SELECT MIN(status_id), MAX(status_id), AVG(status_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY status_id)
FROM registrations;

SELECT MIN(university_id), MAX(university_id), AVG(university_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY university_id)
FROM fos_at_universities;

SELECT MIN(field_of_study_id), MAX(field_of_study_id), AVG(field_of_study_id),
PERCENTILE_CONT(ARRAY[0.25,0.5,0.75]) WITHIN GROUP (ORDER BY field_of_study_id)
FROM fos_at_universities;
