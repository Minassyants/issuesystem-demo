package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.DepartmentFeedback;

//[X] REFACTOR
@Repository
public interface DepartmentFeedbackRepository extends CombinedRepository<DepartmentFeedback, Integer> {

}
