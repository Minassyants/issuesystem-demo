package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Client;

//[x] REFACTOR
@Repository
public interface ClientRepository extends CombinedRepository<Client, Integer> {

}
