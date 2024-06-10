package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.User;

@Repository
public interface UserRepository extends ArangoRepository<User, String> {

}
