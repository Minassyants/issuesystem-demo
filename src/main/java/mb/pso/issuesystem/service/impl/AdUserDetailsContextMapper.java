package mb.pso.issuesystem.service.impl;

import java.util.Collection;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.AdUserDetails;

@Service
public class AdUserDetailsContextMapper extends LdapUserDetailsMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        UserDetails userDetails = super.mapUserFromContext(ctx, username, authorities);
        AdUserDetails user = new AdUserDetails(userDetails.getUsername(), "",
                userDetails.getAuthorities(), ctx.getStringAttribute("mail"),ctx.getStringAttribute("displayName"),ctx.getStringAttribute("givenName"),ctx.getStringAttribute("sn"),ctx.getStringAttribute("sAMAccountName"));
                

        return user;
    }

}
