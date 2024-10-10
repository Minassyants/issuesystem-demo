package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
