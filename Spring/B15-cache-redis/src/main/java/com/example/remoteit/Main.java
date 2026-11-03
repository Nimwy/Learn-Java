package com.example.remoteit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // Bài 15: bật xử lý @Cacheable, @CacheEvict
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
