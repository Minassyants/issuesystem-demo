package mb.pso.issuesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
public class WebConfig {
    private final String allowerdOriginPatterns;

    public WebConfig(@Value("${app.cors.allowed-origin-patterns}") String allowerdOriginPatterns) {
        this.allowerdOriginPatterns = allowerdOriginPatterns;
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns(allowerdOriginPatterns.split(","))
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}
