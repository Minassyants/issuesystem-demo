package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.IssueAttribute;

@Repository
public interface IssueAttributeRepository extends JpaRepository<IssueAttribute, Integer> {

}
