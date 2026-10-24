package com.example.remoteit;

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
        Category backend = categoryRepository.save(new Category("Backend", "backend"));
        Category frontend = categoryRepository.save(new Category("Frontend", "frontend"));
        Category mobile = categoryRepository.save(new Category("Mobile", "mobile"));
        Tag java = tagRepository.save(new Tag("Java", "java"));
        Tag react = tagRepository.save(new Tag("React", "react"));
        Tag kotlin = tagRepository.save(new Tag("Kotlin", "kotlin"));

        saveJob("Java Developer", backend, java);
        saveJob("Kotlin Backend Developer", backend, kotlin);
        saveJob("ReactJS Developer", frontend, react);
        saveJob("React Native Developer", mobile, react);
        saveJob("Android Developer", mobile, kotlin);

        System.out.println("===== Cách 1: findAll() =====");
        jobService.printWithFindAll();

        System.out.println("===== Cách 2: JOIN FETCH =====");
        jobService.printWithJoinFetch();
    }

    private void saveJob(String title, Category category, Tag tag) {
        Job job = new Job(title, category);
        job.addTag(tag);
        jobRepository.save(job);
    }
}