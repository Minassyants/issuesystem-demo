package mb.pso.issuesystem.repository.im;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.Chat;
//[ ] REFACTOR
@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

}
