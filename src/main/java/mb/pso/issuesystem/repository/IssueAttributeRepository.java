package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.IssueAttribute;

public interface IssueAttributeRepository extends ArangoRepository<IssueAttribute, String> {

}
