package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.AdditionalAttribute;
//[ ] REFACTOR
@Repository
public interface AdditionalAttributeRepository extends JpaRepository<AdditionalAttribute, Integer> {

}
