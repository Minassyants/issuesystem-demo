package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import com.arangodb.springframework.repository.ArangoRepository;

import mb.pso.issuesystem.entity.Users;

@Repository
public interface UserRepository extends ArangoRepository<Users, String> {

}
