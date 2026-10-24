package com.example.remoteit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final JobRepository jobRepository;

    public AppRunner(CategoryRepository categoryRepository, JobRepository jobRepository) {
        this.categoryRepository = categoryRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        Category backend = categoryRepository.save(new Category("Backend", "backend"));
        Category frontend = categoryRepository.save(new Category("Frontend", "frontend"));

        // Gán object Category cho Job, Hibernate tự lưu category_id
        jobRepository.save(new Job("Java Developer", backend));
        jobRepository.save(new Job("Golang Developer", backend));
        jobRepository.save(new Job("ReactJS Developer", frontend));

        System.out.println("--- Job thuộc danh mục backend ---");
        for (Job job : jobRepository.findByCategorySlug("backend")) {
            // job.getCategory() trả về cả object Category, không chỉ con số id
            System.out.println(job.getTitle() + " | danh mục: " + job.getCategory().getName());
        }
    }
}