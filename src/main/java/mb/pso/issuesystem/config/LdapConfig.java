package mb.pso.issuesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


@Configuration
@EnableLdapRepositories("mb.pso.issuesystem.repository.*")
public class LdapConfig {

  @Value("${ldap.url}")
  private String ldapUrl;

  @Value("${ldap.user-dn}")
  private String ldapUserDn;

  @Value("${ldap.password}")
  private String ldapPassword;

  @Value("${ldap.base}")
  private String ldapBase;

  @Bean
  ContextSource contextSource() {

    LdapContextSource ldapContextSource = new LdapContextSource();
    ldapContextSource.setUrl(ldapUrl);
    ldapContextSource.setUserDn(ldapUserDn);
    ldapContextSource.setPassword(ldapPassword);
    ldapContextSource.setBase(ldapBase);
    return ldapContextSource;
  }

  @Bean
  LdapTemplate ldapTemplate(ContextSource contextSource) {
    return new LdapTemplate(contextSource);
  }
}
