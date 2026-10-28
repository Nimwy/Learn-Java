package com.example.remoteit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;


@Entity
@Table(name = "sessions")
public class RefreshSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private String tokenHash; 

    private Instant expiresAt;

    public RefreshSession() {}

    public RefreshSession(String email, String tokenHash, Instant expiresAt) {
        this.email = email;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public String getEmail() { return email; }
    public String getTokenHash() { return tokenHash; }
}
