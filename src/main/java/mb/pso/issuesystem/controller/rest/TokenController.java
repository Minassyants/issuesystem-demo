package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.AuthInfo;
import mb.pso.issuesystem.service.impl.core.TokenService;


@Tag(name = "Token", description = "Operations related to token management")
@RestController
public class TokenController {

	private final TokenService tokenService;

	public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Operation(summary = "Generate a JWT token", description = "Generates a JWT token based on the authenticated user details.")
	@PostMapping("/token")
	public ResponseEntity<AuthInfo> token(Authentication authentication) {
		AuthInfo authInfo = tokenService.generateToken(authentication);
		return ResponseEntity.ok(authInfo);
	}
}
