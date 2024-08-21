package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

@Repository
public interface AdditionalAttributeTypeRepository extends JpaRepository<AdditionalAttributeType, Integer> {

}
