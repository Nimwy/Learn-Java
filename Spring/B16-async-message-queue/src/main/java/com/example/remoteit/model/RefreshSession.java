package com.example.remoteit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "sessions")
public class RefreshSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(unique = true)
    private String tokenHash; 

    
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private Instant expiresAt;

    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private Instant createdAt; 

    public RefreshSession() {}

    public RefreshSession(String email, String tokenHash, Instant expiresAt) {
        this.email = email;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.createdAt = Instant.now(); 
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public String getEmail() { return email; }
    public String getTokenHash() { return tokenHash; }
    public Instant getCreatedAt() { return createdAt; } 
}
