package mb.pso.issuesystem.dto;

public class AuthInfo {
    private String displayName;
    private String username;
    private String token;
    private String scope;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public AuthInfo() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String diplayName) {
        this.displayName = diplayName;
    }

}
