package com.example.remoteit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll() // MỚI: ai cũng đăng ký được
                        .requestMatchers(HttpMethod.GET, "/api/jobs").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/jobs").hasRole("HR")
                        .requestMatchers(HttpMethod.PUT, "/api/jobs/*/approve").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
