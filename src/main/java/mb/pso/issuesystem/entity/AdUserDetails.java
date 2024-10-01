package mb.pso.issuesystem.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AdUserDetails extends User {
    private String email;
    private String displayName;

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
