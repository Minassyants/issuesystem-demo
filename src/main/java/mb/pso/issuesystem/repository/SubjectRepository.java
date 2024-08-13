package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
}
