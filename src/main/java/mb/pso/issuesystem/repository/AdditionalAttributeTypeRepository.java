package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

@Repository
public interface AdditionalAttributeTypeRepository extends ArangoRepository<AdditionalAttributeType, String> {

}
