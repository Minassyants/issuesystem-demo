package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.IssueType;
//[ ] REFACTOR
@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, Integer>, QuerydslPredicateExecutor<IssueType> {

}
