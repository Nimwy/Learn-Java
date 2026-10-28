package com.example.remoteit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataSeeder implements CommandLineRunner {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        AppUser admin = new AppUser("admin@remoteit.vn", passwordEncoder.encode("admin123"), "ADMIN", "ACTIVE");
        repository.save(admin);
    }
}
