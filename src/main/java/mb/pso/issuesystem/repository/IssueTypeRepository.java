package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.IssueType;

//[x] REFACTOR
@Repository
public interface IssueTypeRepository extends CombinedRepository<IssueType, Integer> {

}
