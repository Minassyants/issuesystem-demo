package mb.pso.issuesystem.repository.im;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.SurpressedChat;

@Repository
public interface SurpressedChatRepository
        extends JpaRepository<SurpressedChat, Integer>, QuerydslPredicateExecutor<SurpressedChat> {

}
