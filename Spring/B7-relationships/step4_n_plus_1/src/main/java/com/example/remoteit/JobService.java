package com.example.remoteit;

import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final Statistics statistics; 

    public JobService(JobRepository jobRepository, EntityManagerFactory entityManagerFactory) {
        this.jobRepository = jobRepository;
        this.statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
    }

    
    @Transactional(readOnly = true)
    public void printWithFindAll() {
        statistics.clear();
        print(jobRepository.findAll());
        System.out.println("→ Số câu SQL: " + statistics.getPrepareStatementCount());
    }

    
    @Transactional(readOnly = true)
    public void printWithJoinFetch() {
        statistics.clear();
        print(jobRepository.findAllWithCategoryAndTags());
        System.out.println("→ Số câu SQL: " + statistics.getPrepareStatementCount());
    }

    private void print(List<Job> jobs) {
        for (Job job : jobs) {
            
            String line = job.getTitle() + " | " + job.getCategory().getName() + " | tags:";
            for (Tag tag : job.getTags()) {
                line += " " + tag.getName();
            }
            System.out.println(line);
        }
    }
}