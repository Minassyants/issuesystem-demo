package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.im.MessageStatus;


@Repository
public interface MessageStatusRepository extends CombinedRepository<MessageStatus, Integer> {

}
