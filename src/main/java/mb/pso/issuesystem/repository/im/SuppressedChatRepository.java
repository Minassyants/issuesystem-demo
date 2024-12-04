package mb.pso.issuesystem.repository.im;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.SuppressedChat;
import mb.pso.issuesystem.repository.CombinedRepository;

//[ ] REFACTOR
@Repository
public interface SuppressedChatRepository
                extends CombinedRepository<SuppressedChat, Integer> {

}
