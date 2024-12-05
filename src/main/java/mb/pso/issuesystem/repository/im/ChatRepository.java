package mb.pso.issuesystem.repository.im;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.repository.core.CombinedRepository;

//[ ] REFACTOR
@Repository
public interface ChatRepository extends CombinedRepository<Chat, Integer> {

}
