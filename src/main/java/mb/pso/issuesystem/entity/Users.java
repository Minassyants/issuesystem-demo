package mb.pso.issuesystem.entity;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "email, username", unique = true))
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String email;

    private String username;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;

    @Override
    public String toString() {
        return "Users [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
                + ", department=" + department + "]";
    }

    public Users() {
    }

    public Users(String email, String username, String password, Department department) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
