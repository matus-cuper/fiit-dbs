SELECT *
FROM main_table_1 m
WHERE m.name LIKE '%'
AND m.surname LIKE '%'
AND m.birth_at BETWEEN '1900-01-01' AND '2100-01-01'
AND m.gss_avg BETWEEN 1.0 AND 5.0
AND m.r_count BETWEEN 0 AND 65355;
