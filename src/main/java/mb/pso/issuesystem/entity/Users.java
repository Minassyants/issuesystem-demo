package mb.pso.issuesystem.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndexed;
import com.arangodb.springframework.annotation.Ref;

@Document("user")
public class Users {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    @PersistentIndexed(unique = true)
    private String email;
    @PersistentIndexed(unique = true)
    private String username;
    private String password;
    @Ref
    private Department department;

    @Override
    public String toString() {
        return "User [id=" + id + ", arangoId=" + arangoId + ", email=" + email + ", username=" + username + ", password="
                + password + ", department=" + department + "]";
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

    public String getArangoId() {
        return arangoId;
    }

    public void setArangoId(String arangoId) {
        this.arangoId = arangoId;
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
