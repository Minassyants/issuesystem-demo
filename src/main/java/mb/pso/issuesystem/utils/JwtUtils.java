package mb.pso.issuesystem.utils;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public final class JwtUtils {
    /**
     * Extracts the display name of the user from the JWT token.
     */
    public static String extractDisplayName(JwtAuthenticationToken jwt) {
        Jwt principal = (Jwt) jwt.getPrincipal();
        return principal.getClaimAsString("displayname");
    }

    /**
     * Extracts the display name of the user from the JWT token.
     */
    public static String extractDisplayName(Jwt jwt) {
        return jwt.getClaimAsString("displayname");
    }
}
