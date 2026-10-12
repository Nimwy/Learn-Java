package com.example.iocdi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Bật quét @Component trong package này và các package con
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        // Khởi động IoC container của Spring (thay cho MiniContainer ở Bước 3).
        // Spring tự quét, tự tạo bean, tự tiêm. Main không cần làm gì thêm.
        SpringApplication.run(Main.class, args);
    }
}