package com.example.remoteit;

import org.hibernate.LazyInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final JobRepository jobRepository;

    public AppRunner(CategoryRepository categoryRepository, TagRepository tagRepository, JobRepository jobRepository) {
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) {
        Category backend = categoryRepository.save(new Category("Backend", "backend"));
        Tag java = tagRepository.save(new Tag("Java", "java"));
        Tag spring = tagRepository.save(new Tag("Spring", "spring"));
        Tag golang = tagRepository.save(new Tag("Golang", "golang"));

        Job javaJob = new Job("Java Developer", backend);
        javaJob.addTag(java);
        javaJob.addTag(spring);
        jobRepository.save(javaJob); 

        Job goJob = new Job("Golang Developer", backend);
        goJob.addTag(golang);
        jobRepository.save(goJob);

        System.out.println("--- Job có tag java ---");
        for (Job job : jobRepository.findByTagsSlug("java")) {
            System.out.println(job.getTitle());
        }

        System.out.println("--- Thử đọc danh sách tag của job ---");
        Job job = jobRepository.findById(javaJob.getId()).orElseThrow();
        try {
            System.out.println("Số tag: " + job.getTags().size());
        } catch (LazyInitializationException e) {
            
            System.out.println("LỖI: " + e.getClass().getSimpleName());
        }
    }
}