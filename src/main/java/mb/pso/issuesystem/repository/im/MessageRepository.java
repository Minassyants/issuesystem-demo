package mb.pso.issuesystem.repository.im;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.Message;
//[ ] REFACTOR
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>, QuerydslPredicateExecutor<Message> {

    public List<Message> findByChatId(Integer id);

}
