package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.MessageStatus;

@Repository
public interface MessageStatusRepository
        extends JpaRepository<MessageStatus, Integer>, QuerydslPredicateExecutor<MessageStatus> {

}
