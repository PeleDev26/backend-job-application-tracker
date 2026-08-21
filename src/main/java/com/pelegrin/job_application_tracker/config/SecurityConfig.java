package com.pelegrin.job_application_tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Temporal
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/permissions/**", "/roles/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }

}