package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Client;
//[ ] REFACTOR
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
