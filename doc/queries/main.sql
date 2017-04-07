SELECT s.name, s.surname, s.birth_at,
	gss.avg,
	a.count,
	r.count,
	g.count,
	g.count_success
FROM students s
LEFT JOIN
	(
	SELECT gss.student_id, ROUND(AVG(gss.mark), 2) AS avg
	FROM graduations_from_ss gss
	GROUP BY gss.student_id
	) gss ON s.student_id = gss.student_id
LEFT JOIN
	(
	SELECT a.student_id, COUNT(*)
	FROM awards a
	GROUP BY a.student_id
	) a ON s.student_id = a.student_id
LEFT JOIN
	(
	SELECT r.student_id, COUNT(*)
	FROM registrations r
	GROUP BY r.student_id
	) r ON s.student_id = r.student_id
LEFT JOIN
	(
	SELECT g.student_id, COUNT(*),
		SUM(CASE WHEN g.graduated = TRUE THEN 1 ELSE 0 END) AS count_success
	FROM graduations g
	GROUP BY g.student_id
	) g ON s.student_id = g.student_id
