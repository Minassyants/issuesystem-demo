package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}
