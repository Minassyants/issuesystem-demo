package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Employee;

//[x] REFACTOR
@Repository
public interface EmployeeRepository extends CombinedRepository<Employee, String> {

}
