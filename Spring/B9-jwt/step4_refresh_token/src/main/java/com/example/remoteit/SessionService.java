package com.example.remoteit;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SessionService {
    private final RefreshSessionRepository repository;
    private final SecureRandom random = new SecureRandom();

    public SessionService(RefreshSessionRepository repository) {
        this.repository = repository;
    }

    
    public String create(String email) {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        repository.save(new RefreshSession(email, sha256(token), Instant.now().plus(7, ChronoUnit.DAYS)));
        return token; 
    }

    
    @Transactional
    public String consume(String token) {
        RefreshSession session = repository.findByTokenHash(sha256(token))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token không hợp lệ"));
        
        
        if (session.isExpired()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token đã hết hạn");
        }
        repository.delete(session);
        return session.getEmail();
    }

    
    @Transactional
    public void delete(String token) {
        repository.findByTokenHash(sha256(token)).ifPresent(repository::delete);
    }

    private String sha256(String value) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
