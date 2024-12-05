package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Users;

//[x] REFACTOR
@Repository
public interface UserRepository extends CombinedRepository<Users, Integer> {
    Users findByUsername(String username);

}
