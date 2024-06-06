package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

public interface AdditionalAttributeTypeRepository extends ArangoRepository<AdditionalAttributeType,String> {
    
}
