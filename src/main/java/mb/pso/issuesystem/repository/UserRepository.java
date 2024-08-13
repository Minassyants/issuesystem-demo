package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

}
