WITH temp_total AS (
    SELECT t1.type_id, count(t1.id) as total_issues
    FROM issue t1
    WHERE t1.doc_date BETWEEN '2024-01-01' AND '2024-12-12'
    GROUP BY t1.type_id

),
temp_closed AS (
    SELECT t1.type_id, count(t1.id) as total_closed
    FROM issue t1
    WHERE (t1.doc_date BETWEEN '2024-01-01' AND '2024-12-12') AND t1.status = 'CLOSED'
    GROUP BY t1.type_id

)
SELECT 
it.name,
t1.total_issues,
t2.total_closed 
FROM temp_total t1 
LEFT JOIN temp_closed t2  ON t1.type_id = t2.type_id
LEFT JOIN issue_type it ON t1.type_id = it.id