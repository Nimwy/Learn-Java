package com.example.remoteit.config;

import com.example.remoteit.model.Job;
import com.example.remoteit.repository.JobRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JobRepository jobRepository;

    public DataInitializer(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        if (jobRepository.count() == 0) {
            jobRepository.save(new Job("Java Developer", "hr@remoteit.vn", "pending"));
        }
    }
}
