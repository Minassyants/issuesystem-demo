package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.relation.IssueStatusTransition;

public interface IssueStatusTransitionRepository extends ArangoRepository<IssueStatusTransition, String> {

}
