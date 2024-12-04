package mb.pso.issuesystem.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.util.WebUtils;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import mb.pso.issuesystem.service.impl.AdUserAuthoritiesPopulator;
import mb.pso.issuesystem.service.impl.AdUserDetailsContextMapper;
import mb.pso.issuesystem.service.impl.core.UserService;

//[x] REFACTOR
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("classpath:keys/public")
    private RSAPublicKey publicKey;

    @Value("classpath:keys/private")
    private RSAPrivateKey privateKey;

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.domain}")
    private String ldapDomain;

    @Value("${ldap.base}")
    private String ldapSearchBase;

    final UserService userServiceImpl;

    public SecurityConfiguration(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Bean
    @Order(50)
    SecurityFilterChain BasicfilterChain(HttpSecurity http, AdUserDetailsContextMapper adUserDetailsContextMapper,
            AdUserAuthoritiesPopulator adUserAuthoritiesPopulator)
            throws Exception {
        http.securityMatcher("/token")
                .authorizeHttpRequests(t -> t.requestMatchers("/token").permitAll())
                .csrf(t -> t.disable())
                .httpBasic(withDefaults())
                .authenticationManager(
                        new ProviderManager(
                                List.of(daoAuthenticationProvider(),
                                        authenticationProvider(adUserDetailsContextMapper,
                                                adUserAuthoritiesPopulator))))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

    }

    @Bean
    @Order(60)
    SecurityFilterChain JWTfilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(t -> t.requestMatchers("/ws/**").permitAll().anyRequest().authenticated())
                .csrf(t -> t.disable())
                .oauth2ResourceServer(t -> t.jwt(withDefaults()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));
        return http.build();

    }

    // [ ] Check if this func is needed.
    public String tokenExtractor(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null)
            return header.replace("Bearer ", "");
        Cookie cookie = WebUtils.getCookie(request, "access_token");
        if (cookie != null)
            return cookie.getValue();
        return null;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ActiveDirectoryLdapAuthenticationProvider authenticationProvider(
            AdUserDetailsContextMapper adUserDetailsContextMapper,
            AdUserAuthoritiesPopulator adUserAuthoritiesPopulator) {
        ActiveDirectoryLdapAuthenticationProvider ad = new ActiveDirectoryLdapAuthenticationProvider(ldapDomain,
                ldapUrl, ldapSearchBase);
        ad.setConvertSubErrorCodesToExceptions(true);
        ad.setAuthoritiesPopulator(adUserAuthoritiesPopulator);
        ad.setUserDetailsContextMapper(adUserDetailsContextMapper);
        return ad;
    }

    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

}
