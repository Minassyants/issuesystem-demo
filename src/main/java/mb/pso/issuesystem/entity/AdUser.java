package mb.pso.issuesystem.entity;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

//[x] REFACTOR
/**
 * Represents an Active Directory user.
 * 
 * {@code AdUser} class is used to store user information from an Active
 * Directory.
 * Includes only basic details such as name, email, etc.
 */
@Entry(objectClasses = { "user", "top" }, base = "OU=Kazakhstan,OU=Remote Users,DC=ukravto,DC=loc")
public final class AdUser {

    /**
     * Distinguished Name (DN) of the Active Directory user.
     * Used as unique ID.
     */
    @Id
    @JsonIgnore
    private Name dn;

    /**
     * Given name of the user (e.g. Alexandr).
     */
    private String givenName;

    /**
     * Email of the user.
     */
    private String mail;

    /**
     * Surname of the user (e.g. Minassyants).
     */
    private String sn;

    /**
     * Display name of the user (e.g. AK Alexandr Minassyants).
     * This name is used as unique ID in {@link Employee}.
     */
    private String displayName;

    /**
     * Account name used for login.
     */
    private String sAMAccountName;

    public AdUser() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
