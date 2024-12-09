package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.core.AdditionalAttribute;


@Repository
public interface AdditionalAttributeRepository extends CombinedRepository<AdditionalAttribute, Integer> {

}
