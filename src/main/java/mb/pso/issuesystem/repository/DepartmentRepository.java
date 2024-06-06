package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Department;

public interface DepartmentRepository extends ArangoRepository<Department, String> {

}
