package mb.pso.issuesystem.controller.rest;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.dto.AuthInfo;

@RestController
public class TokenController {
	private final JwtEncoder encoder;

	public TokenController(JwtEncoder encoder) {
		this.encoder = encoder;
	}

	@PostMapping("/token")
	public ResponseEntity<AuthInfo> token(Authentication authentication) {
		Instant now = Instant.now();
		// long expiry = 36000L;
		// @formatter:off
		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				// .expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();
		// @formatter:on
		AuthInfo authInfo = new AuthInfo();
		authInfo.setToken(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
		authInfo.setUsername(authentication.getName());
		authInfo.setScope(scope);
		return ResponseEntity.ok(authInfo);
	}
}
