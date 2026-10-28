package com.example.remoteit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String passwordHash; 
    private String role;         
    private String status;       

    public AppUser() {}

    public AppUser(String email, String passwordHash, String role, String status) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.status = status;
    }

    public void activate() {
        this.status = "ACTIVE";
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
}
