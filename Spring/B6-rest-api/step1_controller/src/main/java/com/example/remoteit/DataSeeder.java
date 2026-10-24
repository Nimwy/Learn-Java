package com.example.remoteit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Thêm dữ liệu mẫu khi ứng dụng khởi động (giống AppRunner ở bài 3, bài 5)
@Component
public class DataSeeder implements CommandLineRunner {
    private final CategoryRepository repository;

    public DataSeeder(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.save(new Category("Backend", "backend"));
        repository.save(new Category("Frontend", "frontend"));
        repository.save(new Category("Mobile", "mobile"));
    }
}