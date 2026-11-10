package com.media.social_media_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // CSRF disable for POST requests
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/**").permitAll()
                                                .requestMatchers("/api/posts/**").permitAll()
                                                .requestMatchers("/api/comments/**").permitAll()
                                                .requestMatchers("/api/users/**").permitAll() // (Or authenticated() if
                                                                                              // you are passing token
                                                                                              // in header)
                                                .requestMatchers("/api/likes/**").permitAll() // 👈 1. Likes
                                                                                              // endpoints-kum permit
                                                                                              // panrom
                                                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                                .anyRequest().authenticated());
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}