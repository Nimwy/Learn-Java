package com.example.remoteit;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;


@Component
public class CookieTokenResolver implements BearerTokenResolver {
    private final DefaultBearerTokenResolver headerResolver = new DefaultBearerTokenResolver();

    @Override
    public String resolve(HttpServletRequest request) {
        
        if (request.getRequestURI().startsWith("/api/auth/")) {
            return null;
        }
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("access_token")) {
                    return cookie.getValue();
                }
            }
        }
        return headerResolver.resolve(request); 
    }
}
