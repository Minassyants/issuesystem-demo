package mb.pso.issuesystem.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CombinedRepository<T, ID> extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {

}
