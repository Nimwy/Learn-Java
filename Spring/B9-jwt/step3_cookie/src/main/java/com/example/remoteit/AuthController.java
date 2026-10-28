package com.example.remoteit;

import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(request.email(), request.password()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sai email hoặc mật khẩu");
        }
        String token = tokenService.createAccessToken(authentication.getName(), authentication.getAuthorities());

        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie(token, Duration.ofMinutes(15)).toString())
                .body(new LoginResponse(authentication.getName(), 15 * 60));
    }

    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie("", Duration.ZERO).toString())
                .build();
    }

    private ResponseCookie accessTokenCookie(String value, Duration maxAge) {
        return ResponseCookie.from("access_token", value)
                .httpOnly(true)  
                .secure(false)   
                .sameSite("Lax") 
                .path("/")       
                .maxAge(maxAge)  
                .build();
    }
}
