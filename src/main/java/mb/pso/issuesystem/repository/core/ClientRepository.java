package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.core.Client;


@Repository
public interface ClientRepository extends CombinedRepository<Client, Integer> {

}
