package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

}
