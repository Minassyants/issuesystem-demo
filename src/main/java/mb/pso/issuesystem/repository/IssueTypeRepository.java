package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.IssueType;

public interface IssueTypeRepository extends ArangoRepository<IssueType, String> {

}
