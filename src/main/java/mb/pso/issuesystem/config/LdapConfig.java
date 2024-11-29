package mb.pso.issuesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
//[ ] REFACTOR
@Configuration
@EnableLdapRepositories("mb.pso.issuesystem.repository.*")
public class LdapConfig {
    @Bean
  ContextSource contextSource() {

    LdapContextSource ldapContextSource = new LdapContextSource();

    ldapContextSource.setUserDn("1C_Redmine@ukravto.ua");
    ldapContextSource.setPassword("R*123456");
    ldapContextSource.setBase("OU=Kazakhstan,OU=Remote Users,DC=ukravto,DC=loc");
    ldapContextSource.setUrl("ldap://192.168.50.5:389");

    return ldapContextSource;
  }

  @Bean
  LdapTemplate ldapTemplate(ContextSource contextSource) {
    return new LdapTemplate(contextSource);
  }
}
