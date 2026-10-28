package com.example.remoteit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
               
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error").permitAll() 
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


    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails hr = User.withUsername("hr@remoteit.vn")
                .password(encoder.encode("hr123"))
                .roles("HR")
                .build();
        UserDetails admin = User.withUsername("admin@remoteit.vn")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(hr, admin);
    }
}