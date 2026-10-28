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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Duration ACCESS_TTL = Duration.ofMinutes(15);
    private static final Duration REFRESH_TTL = Duration.ofDays(7);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final SessionService sessionService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                          TokenService tokenService, SessionService sessionService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.sessionService = sessionService;
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
        String accessToken = tokenService.createAccessToken(authentication.getName(), authentication.getAuthorities());
        String refreshToken = sessionService.create(authentication.getName());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie(accessToken, ACCESS_TTL).toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie(refreshToken, REFRESH_TTL).toString())
                .body(new LoginResponse(authentication.getName(), ACCESS_TTL.toSeconds()));
    }

    
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@CookieValue(name = "refresh_token", required = false) String oldToken) {
        if (oldToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Thiếu refresh token");
        }
        String email = sessionService.consume(oldToken); 

        
        UserDetails user = userDetailsService.loadUserByUsername(email);
        String accessToken = tokenService.createAccessToken(user.getUsername(), user.getAuthorities());
        String refreshToken = sessionService.create(email);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie(accessToken, ACCESS_TTL).toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie(refreshToken, REFRESH_TTL).toString())
                .body(new LoginResponse(email, ACCESS_TTL.toSeconds()));
    }

    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken != null) {
            sessionService.delete(refreshToken);
        }
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, accessCookie("", Duration.ZERO).toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie("", Duration.ZERO).toString())
                .build();
    }

    private ResponseCookie accessCookie(String value, Duration maxAge) {
        return ResponseCookie.from("access_token", value)
                .httpOnly(true).secure(false).sameSite("Lax")
                .path("/")
                .maxAge(maxAge)
                .build();
    }

    private ResponseCookie refreshCookie(String value, Duration maxAge) {
        return ResponseCookie.from("refresh_token", value)
                .httpOnly(true).secure(false).sameSite("Lax")
                .path("/api/auth") 
                .maxAge(maxAge)
                .build();
    }
}
