package com.example.remoteit;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final JobRepository jobRepository;
    private final JobService jobService;

    public AppRunner(CategoryRepository categoryRepository, TagRepository tagRepository,
                     JobRepository jobRepository, JobService jobService) {
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.jobRepository = jobRepository;
        this.jobService = jobService;
    }

    @Override
    public void run(String... args) {
        categoryRepository.save(new Category("Backend", "backend"));
        tagRepository.save(new Tag("Java", "java"));
        tagRepository.save(new Tag("Spring", "spring"));

        jobService.createJob("Java Developer", "backend", List.of("java", "spring"));
        System.out.println("Số job ban đầu: " + jobRepository.count());

        System.out.println("--- 1. Không có @Transactional ---");
        try {
            jobService.createJobWithoutTransaction("Kotlin Developer", "backend", List.of("kotlin"));
        } catch (IllegalArgumentException e) {
            System.out.println("LỖI: " + e.getMessage());
        }
        System.out.println("Số job: " + jobRepository.count() + "  ← job lỗi VẪN bị lưu");

        System.out.println("--- 2. Có @Transactional ---");
        try {
            jobService.createJob("Kotlin Developer", "backend", List.of("kotlin"));
        } catch (IllegalArgumentException e) {
            System.out.println("LỖI: " + e.getMessage());
        }
        System.out.println("Số job: " + jobRepository.count() + "  ← đã rollback, không lưu thêm");

        System.out.println("--- 3. Đọc tags trong @Transactional(readOnly = true) ---");
        jobService.printJobs();
    }
}