package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.IssueAttribute;

@Repository
public interface IssueAttributeRepository extends ArangoRepository<IssueAttribute, String> {

}
