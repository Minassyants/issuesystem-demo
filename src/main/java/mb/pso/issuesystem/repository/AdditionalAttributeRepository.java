package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.AdditionalAttribute;

//[x] REFACTOR
@Repository
public interface AdditionalAttributeRepository extends CombinedRepository<AdditionalAttribute, Integer> {

}
