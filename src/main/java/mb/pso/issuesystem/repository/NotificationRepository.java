package mb.pso.issuesystem.repository;

import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Notification;

//[x] REFACTOR
@Repository
public interface NotificationRepository
                extends CombinedRepository<Notification, Integer> {

}
