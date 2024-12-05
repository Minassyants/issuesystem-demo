package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Subject;

//[x] REFACTOR
@Repository
public interface SubjectRepository extends CombinedRepository<Subject, Integer> {
}
