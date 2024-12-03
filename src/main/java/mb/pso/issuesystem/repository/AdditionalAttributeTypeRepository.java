package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

//[x] REFACTOR
@Repository
public interface AdditionalAttributeTypeRepository extends CombinedRepository<AdditionalAttributeType, Integer> {

}
