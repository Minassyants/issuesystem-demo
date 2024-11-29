package mb.pso.issuesystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//[ ] REFACTOR
@Entity
/* Should always match AdUser */
public class Employee {
    @Id
    private String displayName;
    private String givenName;
    private String sn;
    @Column(unique = true)
    private String mail;
    private String sAMAccountName;

    public String getGivenName() {
        return givenName;
    }

    @Override
    public int hashCode() {

        return this.displayName.hashCode();
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Employee() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        return this.displayName.equals(other.displayName);
    }

    public Employee(String displayName, String givenName, String sn, String mail) {
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
        this.mail = mail;
    }

    public boolean isEmpty() {
        return this.displayName == null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Employee(String displayName, String givenName, String sn, String mail, String sAMAccountName) {
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
        this.mail = mail;
        this.sAMAccountName = sAMAccountName;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

}
