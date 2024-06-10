package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.AdditionalAttribute;

@Repository
public interface AdditionalAttributeRepository extends ArangoRepository<AdditionalAttribute, String> {

}
