package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.AttachedFile;

//[x] REFACTOR
@Repository
public interface AttachedFileRepository extends CombinedRepository<AttachedFile, Integer> {

}
