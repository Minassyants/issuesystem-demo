package mb.pso.issuesystem.repository.core;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.core.Notification;


@Repository
public interface NotificationRepository
                extends CombinedRepository<Notification, Integer> {

}
