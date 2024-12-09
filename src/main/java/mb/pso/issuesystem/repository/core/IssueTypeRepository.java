package mb.pso.issuesystem.repository.core;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.core.IssueType;


@Repository
public interface IssueTypeRepository extends CombinedRepository<IssueType, Integer> {
    List<IssueType> findAll(Predicate predicate);
}
