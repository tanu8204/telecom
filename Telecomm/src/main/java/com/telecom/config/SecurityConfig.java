package com.telecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for testing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/products/**").permitAll() // Allow public access
                .anyRequest().authenticated() // Protect other endpoints
            )
            .httpBasic(); // Use HTTP Basic authentication (or replace with JWT/OAuth2 if needed)

        return http.build();
    }
}
