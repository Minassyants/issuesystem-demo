package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Issue;

@Repository
public interface IssueRepository extends ArangoRepository<Issue, String> {

}
