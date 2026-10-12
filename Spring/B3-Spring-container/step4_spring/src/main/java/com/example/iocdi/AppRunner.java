package com.example.iocdi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// CommandLineRunner: Spring tự gọi hàm run() khi đã khởi động xong.
// Class này cũng là một bean, nên Spring tự tiêm JobService vào constructor.
@Component
public class AppRunner implements CommandLineRunner {
    private final JobService jobService;

    public AppRunner(JobService jobService) { // không new, không getBean: Spring đưa vào
        this.jobService = jobService;
    }

    @Override
    public void run(String... args) {
        jobService.approveJob();
    }
}