package com.example.remoteit;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    
    public String createAccessToken(String email, Collection<? extends GrantedAuthority> authorities) {
        
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority().replace("ROLE_", ""));
        }

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder() 
                .issuer("remoteit")                       
                .subject(email)                           
                .issuedAt(now)                            
                .expiresAt(now.plus(15, ChronoUnit.MINUTES)) 
                .claim("roles", roles)                    
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build(); 

        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
