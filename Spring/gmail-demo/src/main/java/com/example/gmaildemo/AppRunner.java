package com.example.gmaildemo;

import java.io.File;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final EmailService emailService;

    public AppRunner(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Thay bằng địa chỉ Gmail của bạn để tự kiểm tra
        String recipient = "your-email@gmail.com";

        emailService.sendEmail(
                recipient,
                "Demo gửi email bằng Spring Boot",
                "Nam",
                "Ứng dụng đã gửi email HTML thành công!",
                List.of()
        );
    }
}