package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.AttachedFile;
//[ ] REFACTOR
@Repository
public interface AttachedFileRepository extends JpaRepository<AttachedFile, Integer> {

}
