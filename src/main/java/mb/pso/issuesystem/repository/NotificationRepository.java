package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import mb.pso.issuesystem.entity.Notification;
//[ ] REFACTOR
@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Integer>, QuerydslPredicateExecutor<Notification> {

}
