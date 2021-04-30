package io.pivotal.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        return http

                .authorizeRequests( authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )

                .formLogin( withDefaults() )

                .build();

    }

    @Bean
    UserDetailsService users() {

        UserDetails client1 = User.withDefaultPasswordEncoder()
                .username( "client1" )
                .password( "password" )
                .roles( "CLIENT" )
                .authorities( "view", "checkout" )
                .build();

        UserDetails client2 = User.withDefaultPasswordEncoder()
                .username( "client2" )
                .password( "password" )
                .roles( "CLIENT" )
                .authorities( "view", "checkout" )
                .build();

        return new InMemoryUserDetailsManager( client1, client2 );
    }

}
