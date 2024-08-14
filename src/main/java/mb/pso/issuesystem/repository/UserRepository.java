package mb.pso.issuesystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Users;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    List<Users> findByUsername(String username);
}
