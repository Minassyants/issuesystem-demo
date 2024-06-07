package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Subject;

public interface SubjectRepository extends ArangoRepository<Subject, String> {

}
