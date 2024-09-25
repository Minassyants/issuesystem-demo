package mb.pso.issuesystem.repository.im;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
