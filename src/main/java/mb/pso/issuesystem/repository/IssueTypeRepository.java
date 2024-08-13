package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.IssueType;

@Repository
public interface IssueTypeRepository extends JpaRepository<IssueType, String> {

}
