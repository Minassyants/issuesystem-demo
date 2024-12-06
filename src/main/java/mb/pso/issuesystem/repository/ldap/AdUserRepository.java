package mb.pso.issuesystem.repository.ldap;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.AdUser;

//[x] REFACTOR
public interface AdUserRepository extends LdapRepository<AdUser>, QuerydslPredicateExecutor<AdUser> {

    List<AdUser> findAll(Predicate predicate);
}
