package com.example.remoteit;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    
    @GetMapping("/api/me")
    public MeResponse me(Authentication authentication) {
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        return new MeResponse(authentication.getName(), roles);
    }
}
