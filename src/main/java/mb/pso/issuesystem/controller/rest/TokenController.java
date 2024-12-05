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
import mb.pso.issuesystem.entity.AdUserDetails;
//[ ] REFACTOR
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

		String scope = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		AdUserDetails userDetails = (AdUserDetails) authentication.getPrincipal();
		String email = userDetails.getEmail();
		String displayName = userDetails.getDisplayName();
		String givenName = userDetails.getGivenName();
		String sn = userDetails.getSn();
		String sAMAccountName = userDetails.getsAMAccountName();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("self")
				.issuedAt(now)
				// .expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName())
				.claim("scope", scope)
				.claim("email", email)
				.claim("displayname", displayName)
				.claim("samaccountname", sAMAccountName)
				.build();

		AuthInfo authInfo = new AuthInfo();
		authInfo.setDisplayName(displayName);
		authInfo.setGivenName(givenName);
		authInfo.setSn(sn);
		authInfo.setMail(email);
		authInfo.setsAMAccountName(sAMAccountName);
		authInfo.setToken(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
		authInfo.setUsername(authentication.getName());
		authInfo.setScope(scope);
		return ResponseEntity.ok(authInfo);
	}
}
