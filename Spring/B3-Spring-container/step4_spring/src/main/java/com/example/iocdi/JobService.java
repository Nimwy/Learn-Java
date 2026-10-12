package com.example.iocdi;

import org.springframework.stereotype.Service;

// @Service cũng là @Component, chỉ đặt tên cho rõ đây là tầng nghiệp vụ
@Service
public class JobService {
    private final MessageSender sender; // final: gán một lần trong constructor, không đổi nữa

    // Chỉ có 1 constructor → Spring tự tiêm, không cần @Autowired
    public JobService(MessageSender sender) {
        this.sender = sender;
    }

    public void approveJob() {
        sender.send("Tin tuyển dụng đã được duyệt");
    }
}