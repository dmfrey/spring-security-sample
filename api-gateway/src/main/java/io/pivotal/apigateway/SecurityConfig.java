package io.pivotal.apigateway;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CorsSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain( ServerHttpSecurity http ) {

        return http

                .cors( CorsSpec::disable )
                .csrf( CsrfSpec::disable )

//                .requestCache( requestCache ->
//                        requestCache.requestCache( NoOpServerRequestCache.getInstance() )
//                )
//
                .authorizeExchange( authorizeExchange -> authorizeExchange
                        .matchers( EndpointRequest.toAnyEndpoint() ).permitAll()
                        .pathMatchers( HttpMethod.GET, "/api/purchases" ).hasAuthority( "SCOPE_view" )
                        .anyExchange().permitAll()
                )

                .oauth2Login( withDefaults() )

                .build();
    }

    @Bean
    HttpTraceRepository httpTraceRepository() {

        return new InMemoryHttpTraceRepository();
    }
}
