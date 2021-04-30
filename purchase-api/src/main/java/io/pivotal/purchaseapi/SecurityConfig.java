package io.pivotal.purchaseapi;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain( ServerHttpSecurity http ) {

        return http

                .csrf( CsrfSpec::disable )

//                .requestCache( requestCache ->
//                        requestCache.requestCache( NoOpServerRequestCache.getInstance() )
//                )

                .authorizeExchange( exchanges ->
                        exchanges
                                .matchers( EndpointRequest.toAnyEndpoint() ).permitAll()
                                .pathMatchers( "/api/purchases" ).hasAuthority( "SCOPE_view" )
                                .anyExchange().authenticated()
                )

                .oauth2ResourceServer( OAuth2ResourceServerSpec::jwt )

                .build();
    }

}
