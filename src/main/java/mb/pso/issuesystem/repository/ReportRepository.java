package mb.pso.issuesystem.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.query.criteria.JpaSubQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import mb.pso.issuesystem.dto.BasicReport;
import mb.pso.issuesystem.entity.QIssue;
import mb.pso.issuesystem.entity.enums.IssueStatus;

@Component
public class ReportRepository {
    private final EntityManager entityManager;

    public ReportRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Query(nativeQuery = true,value = """
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
            """)
    BasicReport fetchReport(Date start, Date end) {
        
    }
}
