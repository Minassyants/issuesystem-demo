package mb.pso.issuesystem.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtDecoder jwtDecoder;

    public WebSocketConfig(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Bean
    WebSocketContainerFactoryBean createWebSocketContainer() {
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxSessionIdleTimeout(10000);
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ThreadPoolTaskScheduler pingScheduler = new ThreadPoolTaskScheduler();
        pingScheduler.initialize();

        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[] { 7000, 7000 })
                .setTaskScheduler(pingScheduler);
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorImplementation());
    }

    private final class ChannelInterceptorImplementation implements ChannelInterceptor {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {

            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                authenticateUser(accessor);
            }
            return message;
        }
    }

    private void authenticateUser(StompHeaderAccessor accessor) {
        List<String> authHeaders = accessor.getNativeHeader(HttpHeaders.AUTHORIZATION);
        String token = authHeaders.get(0).replace("Bearer ", "");

        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
        jwtAuthenticationProvider.setJwtAuthenticationConverter(new JwtAuthenticationConverter());

        BearerTokenAuthenticationToken authenticationToken = new BearerTokenAuthenticationToken(
                token);
        Authentication user = jwtAuthenticationProvider.authenticate(authenticationToken);

        accessor.setUser(user);
    }

}
