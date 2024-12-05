package mb.pso.issuesystem.service.impl.ldap;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.AdUser;
import mb.pso.issuesystem.entity.QAdUser;
import mb.pso.issuesystem.repository.ldap.AdUserRepository;

//[ ] REFACTOR
@Service
public class AdUserService {

    private final AdUserRepository repository;

    public AdUserService(AdUserRepository repository) {
        this.repository = repository;
    }

    public AdUser getByDisplayNameOrThrow(String displayName) {
        Predicate predicate = QAdUser.adUser.displayName.eq(displayName);
        return repository.findOne(predicate).orElseThrow(() -> new IllegalArgumentException(
                String.format("User `%s` not found in Active Directory", displayName)));

    }

    public Iterable<AdUser> findAllByGivenNameSn(String queryString) {
        QAdUser adUser = QAdUser.adUser;
        queryString = queryString.replace(" ", "*");
        Predicate predicate = adUser.mail.isNotNull().and(adUser.sn.isNotNull()).and(adUser.givenName.isNotNull())
                .and(adUser.displayName.isNotNull()).and(adUser.displayName.like("*".concat(queryString).concat("*")));
        return repository.findAll(predicate);
    }

}
