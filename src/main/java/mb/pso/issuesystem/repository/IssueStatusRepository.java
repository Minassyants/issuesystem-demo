package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.IssueStatus;

public interface IssueStatusRepository extends ArangoRepository<IssueStatus, String> {

}
