package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Subject;

@Repository
public interface SubjectRepository extends ArangoRepository<Subject, String> {
}
