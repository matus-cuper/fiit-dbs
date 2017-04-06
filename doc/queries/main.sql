WITH avg_gss AS
	(
	SELECT s.student_id,
		CASE WHEN ROUND(AVG(gss.mark), 2) IS NULL THEN 5.00
		ELSE ROUND(AVG(gss.mark), 2) END AS avg
	FROM students s
	LEFT JOIN graduations_from_ss gss ON s.student_id = gss.student_id
	GROUP BY s.student_id
	),
count_a AS
	(
	SELECT s.student_id, COUNT(a.award_id)
	FROM students s
	LEFT JOIN awards a ON s.student_id = a.student_id
	GROUP BY s.student_id
	),
count_r AS
	(
	SELECT s.student_id, COUNT(r.registration_id)
	FROM students s
	LEFT JOIN registrations r ON s.student_id = r.student_id
	GROUP BY s.student_id
	),
count_g AS
	(
	SELECT s.student_id, COUNT(g.graduation_id),
		SUM(CASE WHEN g.graduated = TRUE THEN 1 ELSE 0 END) AS count_success
	FROM students s
	LEFT JOIN graduations g ON s.student_id = g.student_id
	GROUP BY s.student_id
	)

SELECT s.name, s.surname, s.birth_at, gss.avg, a.count, r.count, g.count, g.count_success
FROM students s
JOIN avg_gss gss ON s.student_id = gss.student_id
JOIN count_a a ON s.student_id = a.student_id
JOIN count_r r ON s.student_id = r.student_id
JOIN count_g g ON s.student_id = g.student_id
