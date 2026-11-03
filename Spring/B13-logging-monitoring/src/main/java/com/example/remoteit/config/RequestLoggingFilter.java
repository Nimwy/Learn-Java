package com.example.remoteit.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10) 
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger("http.access");
    public static final String REQUEST_ID = "requestId"; 
    private static final String HEADER = "X-Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestId = request.getHeader(HEADER);
        if (requestId == null || !requestId.matches("[A-Za-z0-9_-]{1,64}")) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(REQUEST_ID, requestId);          
        response.setHeader(HEADER, requestId);    

        long start = System.nanoTime();
        int status = 500; 
        try {
            chain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            long ms = (System.nanoTime() - start) / 1_000_000;
            try {
                log.info("{} {} -> {} {}ms",
                        request.getMethod(), request.getRequestURI(), status, ms);
            } finally {
                MDC.remove(REQUEST_ID); 
            }
        }
    }
}
