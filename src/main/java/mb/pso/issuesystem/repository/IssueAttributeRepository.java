package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.IssueAttribute;
//[ ] REFACTOR
@Repository
public interface IssueAttributeRepository extends JpaRepository<IssueAttribute, Integer>, QuerydslPredicateExecutor<IssueAttribute> {

}
