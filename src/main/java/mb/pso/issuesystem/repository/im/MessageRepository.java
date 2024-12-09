package mb.pso.issuesystem.repository.im;

import java.util.List;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.repository.core.CombinedRepository;


@Repository
public interface MessageRepository extends CombinedRepository<Message, Integer> {

    public List<Message> findByChatId(Integer id);

}
