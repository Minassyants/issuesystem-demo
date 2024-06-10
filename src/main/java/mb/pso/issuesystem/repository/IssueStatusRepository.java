package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.IssueStatus;

@Repository
public interface IssueStatusRepository extends ArangoRepository<IssueStatus, String> {

}
