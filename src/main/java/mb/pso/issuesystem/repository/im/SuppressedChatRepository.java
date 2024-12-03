package mb.pso.issuesystem.repository.im;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.SuppressedChat;
//[ ] REFACTOR
@Repository
public interface SuppressedChatRepository
        extends JpaRepository<SuppressedChat, Integer>, QuerydslPredicateExecutor<SuppressedChat> {

}
