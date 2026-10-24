package com.example.remoteit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // Có spring-boot-starter-web → Spring tự chạy web server (Tomcat) ở cổng 8080
        SpringApplication.run(Main.class, args);
    }
}