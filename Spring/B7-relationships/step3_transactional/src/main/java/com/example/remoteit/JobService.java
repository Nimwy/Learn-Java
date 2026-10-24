package com.example.remoteit;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final JobRepository jobRepository;

    public JobService(CategoryRepository categoryRepository, TagRepository tagRepository, JobRepository jobRepository) {
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.jobRepository = jobRepository;
    }


    public Job createJob(String title, String categorySlug, List<String> tagSlugs) {
        return doCreate(title, categorySlug, tagSlugs);
    }


    public Job createJobWithoutTransaction(String title, String categorySlug, List<String> tagSlugs) {
        return doCreate(title, categorySlug, tagSlugs);
    }

    private Job doCreate(String title, String categorySlug, List<String> tagSlugs) {
        Category category = categoryRepository.findBySlug(categorySlug)
                .orElseThrow(() -> new IllegalArgumentException("Không có danh mục: " + categorySlug));

        Job job = jobRepository.save(new Job(title, category)); 

        for (String slug : tagSlugs) {                          
            Tag tag = tagRepository.findBySlug(slug)
                    .orElseThrow(() -> new IllegalArgumentException("Không có tag: " + slug));
            job.addTag(tag);
        }
        return job;
    }


    @Transactional(readOnly = true)
    public void printJobs() {
        for (Job job : jobRepository.findAll()) {
            System.out.print(job.getTitle() + " | tags:");
            for (Tag tag : job.getTags()) {
                System.out.print(" " + tag.getName());
            }
            System.out.println();
        }
    }
}