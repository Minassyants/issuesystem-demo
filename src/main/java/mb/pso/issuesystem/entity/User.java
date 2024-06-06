package mb.pso.issuesystem.entity;

import org.springframework.data.annotation.Id;

import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndexed;
import com.arangodb.springframework.annotation.Ref;

@Document("user")
public class User {
    @Id
    private String id;
    @ArangoId
    private String arangoId;
    @PersistentIndexed(unique = true)
    private String email;
    @PersistentIndexed(unique = true)
    private String login;
    private String password;
    @Ref
    private Department department;

    @Override
    public String toString() {
        return "User [id=" + id + ", arangoId=" + arangoId + ", email=" + email + ", login=" + login + ", password="
                + password + ", department=" + department + "]";
    }

    public User() {
    }

    public User(String email, String login, String password, Department department) {
        this.email = email;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
