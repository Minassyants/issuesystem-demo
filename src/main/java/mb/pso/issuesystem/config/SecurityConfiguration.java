package mb.pso.issuesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import mb.pso.issuesystem.service.impl.AdUserDetailsContextMapper;
import mb.pso.issuesystem.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("classpath:keys/public")
    RSAPublicKey key;

    @Value("classpath:keys/private")
    RSAPrivateKey priv;

    final UserServiceImpl userServiceImpl;

    public SecurityConfiguration(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    // [ ] Добавить исключение для клиентской формы принятия жалоб
    @Bean
    @Order(1)
    SecurityFilterChain BasicfilterChain(HttpSecurity http, AdUserDetailsContextMapper adUserDetailsContextMapper
    // ,
    // ActiveDirectoryLdapAuthenticationProvider
    // activeDirectoryLdapAuthenticationProvider,
    // DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        http.securityMatcher("/token")
                .authorizeHttpRequests(t -> t.requestMatchers("/token").permitAll())
                .csrf(t -> t.disable())
                .httpBasic(withDefaults())
                .authenticationManager(new ProviderManager(
                        List.of(daoAuthenticationProvider(), authenticationProvider(adUserDetailsContextMapper))))
                // .authenticationProvider(daoAuthenticationProvider)
                // .authenticationProvider(activeDirectoryLdapAuthenticationProvider)
                // .authenticationManager(new ProviderManager(
                // List.of(daoAuthenticationProvider,
                // activeDirectoryLdapAuthenticationProvider)))
                // .authenticationManager(authentication -> {
                // Authentication x;
                // try {
                // x = daoAuthenticationProvider.authenticate(authentication);
                // assert x != null;
                // } catch (Exception e) {
                // x = activeDirectoryLdapAuthenticationProvider.authenticate(authentication);
                // }
                // return x;
                // })
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();

    }

    @Bean
    @Order(2)
    SecurityFilterChain JWTfilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(t -> t.anyRequest().authenticated())
                .csrf(t -> t.disable())
                .oauth2ResourceServer(t -> t.jwt(withDefaults()))
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));
        return http.build();

    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    ActiveDirectoryLdapAuthenticationProvider authenticationProvider(
            AdUserDetailsContextMapper adUserDetailsContextMapper) {
        ActiveDirectoryLdapAuthenticationProvider ad = new ActiveDirectoryLdapAuthenticationProvider("ukravto.ua",
                "ldap://192.168.50.5:389", "OU=Kazakhstan,OU=Remote Users,DC=ukravto,DC=loc");
        ad.setConvertSubErrorCodesToExceptions(true);
        ad.setAuthoritiesPopulator((userData, username) -> List.of(new SimpleGrantedAuthority("employee")));
        ad.setUserDetailsContextMapper(adUserDetailsContextMapper);
        // DefaultActiveDirectoryAuthoritiesPopulator
        // ad.setUseAuthenticationRequestCredentials(true);
        return ad;
    }

    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http,
    // DaoAuthenticationProvider daoAuthenticationProvider,
    // ActiveDirectoryLdapAuthenticationProvider
    // activeDirectoryLdapAuthenticationProvider) throws Exception {
    // AuthenticationManagerBuilder authenticationManagerBuilder = http
    // .getSharedObject(AuthenticationManagerBuilder.class);
    // return
    // authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider)
    // .authenticationProvider(activeDirectoryLdapAuthenticationProvider).build();
    // }

}
