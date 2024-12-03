package mb.pso.issuesystem.repository.ldap;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


import mb.pso.issuesystem.entity.AdUser;
//[x] REFACTOR
public interface AdUserRepository extends LdapRepository<AdUser>, QuerydslPredicateExecutor<AdUser> {

}
