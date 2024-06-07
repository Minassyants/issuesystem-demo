package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Client;

@Repository
public interface ClientRepository extends ArangoRepository<Client, String> {

}
