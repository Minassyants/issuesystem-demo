package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.DepartmentFeedback;

@Repository
public interface DepartmentFeedbackRepository extends JpaRepository<DepartmentFeedback, Integer> {

}
