package mb.pso.issuesystem.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.BasicReportRow;
import mb.pso.issuesystem.entity.Issue;
//[ ] REFACTOR
@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer>, QuerydslPredicateExecutor<Issue> {
    /**
     * @param start
     * @param end
     * @return
     */
    @Query(nativeQuery = true, value = """
            WITH temp_total AS (
            SELECT t1.type_id, count(t1.id) as total_issues
            FROM issue t1
            WHERE t1.doc_date BETWEEN ?1 AND ?2
            GROUP BY t1.type_id
            ),
            temp_closed AS (
                SELECT t1.type_id, count(t1.id) as total_closed
                FROM issue t1
                WHERE (t1.doc_date BETWEEN ?1 AND ?2) AND t1.status = 'CLOSED'
                GROUP BY t1.type_id
            ),
            temp_working AS (
                SELECT t1.type_id, count(t1.id) as total_working
                FROM issue t1
                WHERE (t1.doc_date BETWEEN '2024-01-01' AND '2024-12-12') AND t1.status <> 'CLOSED' AND t1.status <> 'NEW'
                GROUP BY t1.type_id
            )
            SELECT
            it.name as type,
            COALESCE(t1.total_issues,0) as total_issues,
            COALESCE(t2.total_closed,0) as total_closed,
            COALESCE(ROUND( (COALESCE(t2.total_closed,0)::numeric / NULLIF(t1.total_issues,0) ) * 100),0)::BIGINT as total_closed_pct ,
            COALESCE(t3.total_working,0) as total_working,
            COALESCE(ROUND( (COALESCE(t3.total_working,0)::numeric / NULLIF(t1.total_issues,0) ) * 100),0)::BIGINT as total_working_pct
            FROM issue_type it
            LEFT JOIN temp_total t1 ON t1.type_id = it.id
            LEFT JOIN temp_closed t2 ON it.id = t2.type_id
            LEFT JOIN temp_working t3 ON it.id = t3.type_id
            """)
    Iterable<BasicReportRow> fetchReport(LocalDate start, LocalDate end);
}
