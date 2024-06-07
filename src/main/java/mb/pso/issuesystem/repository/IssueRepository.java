package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Issue;

public interface IssueRepository extends ArangoRepository<Issue, String> {

}
