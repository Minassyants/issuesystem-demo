package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Department;

@Repository
public interface DepartmentRepository extends ArangoRepository<Department, String> {

}
