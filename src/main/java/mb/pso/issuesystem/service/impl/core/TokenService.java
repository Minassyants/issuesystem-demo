package mb.pso.issuesystem.service.impl.core;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.dto.AuthInfo;
import mb.pso.issuesystem.entity.core.AdUserDetails;


@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public AuthInfo generateToken(Authentication authentication) {
        Instant now = Instant.now();

        AdUserDetails userDetails = (AdUserDetails) authentication.getPrincipal();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("email", userDetails.getEmail())
                .claim("displayname", userDetails.getDisplayName())
                .claim("samaccountname", userDetails.getsAMAccountName())
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        AuthInfo authInfo = new AuthInfo();
        authInfo.setDisplayName(userDetails.getDisplayName());
        authInfo.setGivenName(userDetails.getGivenName());
        authInfo.setSn(userDetails.getSn());
        authInfo.setMail(userDetails.getEmail());
        authInfo.setsAMAccountName(userDetails.getsAMAccountName());
        authInfo.setToken(token);
        authInfo.setUsername(authentication.getName());
        authInfo.setScope(scope);

        return authInfo;
    }
}