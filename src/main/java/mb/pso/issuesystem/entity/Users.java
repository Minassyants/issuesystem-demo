package mb.pso.issuesystem.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import mb.pso.issuesystem.entity.enums.Roles;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email", "username" }) })
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String email;
    private String username;
    private String password;
    private String sAMAccountName;
    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;
    private List<Roles> roles;

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
                + ", department=" + department + "]";
    }

    public Collection<GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return authorities;
    }

    public Users() {

    }

    public Users(String email, String username, String password, Department department) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

}
