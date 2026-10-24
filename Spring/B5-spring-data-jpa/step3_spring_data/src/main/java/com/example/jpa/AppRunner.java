package com.example.jpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final CategoryRepository repository;

    public AppRunner(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        // 1. Lưu dữ liệu
        repository.save(new Category("Backend", "backend"));
        repository.save(new Category("Frontend", "frontend"));
        repository.save(new Category("Mobile", "mobile"));

        // 2. Các hàm có sẵn
        System.out.println(
            "findById(2): " + repository.findById(2L).orElse(null)
        );

        System.out.println("findAll: " + repository.findAll());

        // 3. Các hàm được Spring tự triển khai
        System.out.println(
            "findBySlug: " + repository.findBySlug("mobile")
        );

        System.out.println(
            "findByNameContaining(\"end\"): "
            + repository.findByNameContaining("end")
        );

        // 4. Xem lớp triển khai thực tế
        System.out.println(
            "Class thật của repository: "
            + repository.getClass().getName()
        );
    }
}