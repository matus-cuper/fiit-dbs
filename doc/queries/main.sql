SELECT *
FROM
	(
	SELECT s.student_id, s.name, s.surname, s.birth_at,
		COALESCE(gss.avg, 5.00) AS gss_avg,
		COALESCE(a.count, 0) AS a_count,
		COALESCE(r.count, 0) AS r_count,
		COALESCE(g.count, 0) AS g_count_all,
		COALESCE(g.count_success, 0) AS g_count_success
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
	WHERE s.name LIKE '%'
	AND s.surname LIKE '%'
	AND s.birth_at BETWEEN '1900-01-01' AND '2100-01-01'
	) nt
WHERE nt.gss_avg BETWEEN 1.0 AND 5.0
AND nt.r_count BETWEEN 0 AND 65355
OFFSET 100
LIMIT 100;
