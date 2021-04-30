package io.pivotal.authserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.pivotal.authserver.jose.Jwks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;

import java.util.UUID;
import java.util.function.Supplier;

@Configuration( proxyBeanMethods = false )
@Import( OAuth2AuthorizationServerConfiguration.class )
public class AuthorizationServerConfig {

    @Bean
    RegisteredClientRepository registeredClientRepository() {

        RegisteredClient apigateway = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId( "api-gateway" )
                .clientSecret( "572a20c6-be82-4264-94a9-c38532c410d5" )
                .clientAuthenticationMethod( ClientAuthenticationMethod.BASIC )
                .authorizationGrantType( AuthorizationGrantType.AUTHORIZATION_CODE )
                .redirectUri( "http://localhost:8765/login/oauth2/code/api-gateway" )
                .scope( OidcScopes.OPENID )
//                .scope( OidcScopes.EMAIL )
//                .scope( OidcScopes.PROFILE )
                .scope( "view" )
                .scope( "checkout" )
                .build();

        return new InMemoryRegisteredClientRepository( apigateway );
    }

    @Bean
    Supplier<JWKSet> jwkSetProvider() {

        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet( rsaKey );

        return () -> jwkSet;
    }

    @Bean
    JWKSource<SecurityContext> jwkSource( Supplier<JWKSet> jwkSetProvider ) {

        return ( jwkSelector, securityContext ) -> jwkSelector.select( jwkSetProvider.get() );
    }

    @Bean
    ProviderSettings providerSettings() {

        return new ProviderSettings().issuer( "http://auth-server:8080" );
    }

}
