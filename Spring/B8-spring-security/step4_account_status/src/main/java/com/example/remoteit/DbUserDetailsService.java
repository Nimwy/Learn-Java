package com.example.remoteit;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class DbUserDetailsService implements UserDetailsService {
    private final AppUserRepository repository;

    public DbUserDetailsService(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        AppUser user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không có user: " + email));

        
        return User.withUsername(user.getEmail())
                .password(user.getPasswordHash())
                .roles(user.getRole())
                .disabled(!user.getStatus().equals("ACTIVE")) 
                .build();
    }
}
