package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.core.Employee;


@Repository
public interface EmployeeRepository extends CombinedRepository<Employee, String> {

}
