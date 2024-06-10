package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.relation.IssueStatusTransition;

@Repository
public interface IssueStatusTransitionRepository extends ArangoRepository<IssueStatusTransition, String> {

}
