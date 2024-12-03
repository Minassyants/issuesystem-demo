package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.IssueAttribute;

//[x] REFACTOR
@Repository
public interface IssueAttributeRepository extends CombinedRepository<IssueAttribute, Integer> {

}
