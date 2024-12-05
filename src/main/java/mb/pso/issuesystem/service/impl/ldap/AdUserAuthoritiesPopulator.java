package mb.pso.issuesystem.service.impl.ldap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Service;

@Service
public class AdUserAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ldap.operator-group-name}")
    private String operatorsGroupName;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
            String username) {
        String[] groups = userData.getStringAttributes("memberOf");
        if (groups == null) {
            this.logger.debug("No values for 'memberOf' attribute.");
            return AuthorityUtils.NO_AUTHORITIES;
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("'memberOf' attribute values: " + Arrays.asList(groups));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String group : groups) {
            @SuppressWarnings("deprecation")
            String groupName = new DistinguishedName(group).removeLast().getValue();

            if (groupName.equals(operatorsGroupName))
                authorities.add(new SimpleGrantedAuthority("operator"));
        }

        if (authorities.size() < 1)
            authorities.add(new SimpleGrantedAuthority("employee"));

        return authorities;
    }

}
