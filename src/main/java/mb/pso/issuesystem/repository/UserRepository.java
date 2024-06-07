package mb.pso.issuesystem.repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.User;

public interface UserRepository extends ArangoRepository<User, String> {

}
