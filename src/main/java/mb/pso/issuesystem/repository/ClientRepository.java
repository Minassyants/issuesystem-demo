package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Client;

public interface ClientRepository extends ArangoRepository<Client, String> {

}
