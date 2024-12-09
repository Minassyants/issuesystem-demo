package mb.pso.issuesystem.entity.core;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * Represents an Active Directory user information during authorization.
 * 
 * For more details, see {@link AdUser}.
 * 
 * @see AdUser
 */
public class AdUserDetails extends User {

    private String email;

    private String displayName;

    private String givenName;

    private String sn;

    private String sAMAccountName;

    public AdUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String email, String displayName, String givenName, String sn) {
        super(username, password, authorities);
        this.email = email;
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
    }

    public AdUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            String email, String displayName, String givenName, String sn) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
    }

    public AdUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String email, String displayName, String givenName, String sn, String sAMAccountName) {
        super(username, password, authorities);
        this.email = email;
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
        this.sAMAccountName = sAMAccountName;
    }

    public AdUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            String email, String displayName, String givenName, String sn, String sAMAccountName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
        this.sAMAccountName = sAMAccountName;
    }

    public AdUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String email, String displayName) {
        super(username, password, authorities);
        this.email = email;
        this.displayName = displayName;
    }

    public AdUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            String email, String displayName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
        this.displayName = displayName;
    }

    public AdUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            String email) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.email = email;
    }

    public AdUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String email) {
        super(username, password, authorities);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

}
