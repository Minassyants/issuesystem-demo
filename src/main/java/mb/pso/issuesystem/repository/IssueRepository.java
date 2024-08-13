package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {

}
