package mb.pso.issuesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CombinedRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {

}
