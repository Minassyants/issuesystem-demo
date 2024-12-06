package mb.pso.issuesystem.repository.core;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.IssueAttribute;

//[x] REFACTOR
@Repository
public interface IssueAttributeRepository extends CombinedRepository<IssueAttribute, Integer> {

    List<IssueAttribute> findAll(Predicate predicate);
}
