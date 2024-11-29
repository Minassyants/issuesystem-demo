package mb.pso.issuesystem.dto;
//[ ] REFACTOR
public class AuthInfo {
    private String sAMAccountName;
    private String displayName;
    private String givenName;
    private String sn;
    private String mail;
    private String username;
    private String token;
    private String scope;

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public AuthInfo(String sAMAccountName, String displayName, String givenName, String sn, String mail,
            String username, String token, String scope) {
        this.sAMAccountName = sAMAccountName;
        this.displayName = displayName;
        this.givenName = givenName;
        this.sn = sn;
        this.mail = mail;
        this.username = username;
        this.token = token;
        this.scope = scope;
    }

    public AuthInfo() {
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

}
