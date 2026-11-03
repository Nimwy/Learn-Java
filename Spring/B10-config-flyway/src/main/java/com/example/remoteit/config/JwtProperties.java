package com.example.remoteit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private String secret;

    private int accessTtlMinutes;

    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }

    public int getAccessTtlMinutes() { return accessTtlMinutes; }
    public void setAccessTtlMinutes(int accessTtlMinutes) { this.accessTtlMinutes = accessTtlMinutes; }
}
