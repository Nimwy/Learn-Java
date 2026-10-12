package com.example.gmaildemo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(
            JavaMailSender mailSender,
            TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(
            String to,
            String subject,
            String recipientName,
            String message,
            List<File> attachments)
            throws MessagingException, IOException {

        // 1. Chuẩn bị dữ liệu cho template HTML
        Context context = new Context();
        context.setVariable("recipientName", recipientName);
        context.setVariable("message", message);

        // 2. Render template thành chuỗi HTML hoàn chỉnh
        String html = templateEngine.process(
                "email-template", context);

        // 3. Tạo email hỗ trợ HTML và file đính kèm
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        // 4. Thêm các file đính kèm
        for (File file : attachments) {
            if (!file.isFile() || !file.canRead()) {
                throw new IOException(
                        "Không thể đọc file: " + file.getAbsolutePath());
            }

            FileSystemResource resource =
                    new FileSystemResource(file);

            helper.addAttachment(file.getName(), resource);
        }

        // 5. Gửi email qua SMTP
        mailSender.send(mimeMessage);

        System.out.println("Đã gửi email tới: " + to);
    }
}