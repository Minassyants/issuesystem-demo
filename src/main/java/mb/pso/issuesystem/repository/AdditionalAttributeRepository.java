package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.AdditionalAttribute;

public interface AdditionalAttributeRepository extends ArangoRepository<AdditionalAttribute, String> {

}
