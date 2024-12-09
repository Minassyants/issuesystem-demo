package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.core.DepartmentFeedback;

//[X] REFACTOR
@Repository
public interface DepartmentFeedbackRepository extends CombinedRepository<DepartmentFeedback, Integer> {

}
