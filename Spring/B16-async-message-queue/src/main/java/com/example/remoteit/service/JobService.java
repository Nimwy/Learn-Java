package com.example.remoteit.service;

import com.example.remoteit.event.JobCreatedEvent;
import com.example.remoteit.model.Job;
import com.example.remoteit.repository.JobRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobService {
    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private final JobRepository jobRepository;
    private final ApplicationEventPublisher events;

    public JobService(JobRepository jobRepository, ApplicationEventPublisher events) {
        this.jobRepository = jobRepository;
        this.events = events;
    }

    public List<Job> findAll() {
        log.info("Lấy danh sách job từ database");
        return jobRepository.findAll();
    }

    @Cacheable(cacheNames = "job", key = "#id")
    public Job findById(long id) {
        log.info("Cache MISS: đọc job {} từ database", id);
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không có job " + id));
    }

    @Transactional
    public Job create(String title, String postedBy) {
        Job job = jobRepository.save(new Job(title, postedBy, "pending"));
        events.publishEvent(new JobCreatedEvent(job.getId(), job.getTitle(), job.getPostedBy()));
        return job;
    }

    public List<Job> findPendingOlderThan(Duration age) {
        Instant threshold = Instant.now().minus(age);
        return jobRepository.findByStatusAndCreatedAtBefore("pending", threshold);
    }

    @Transactional
    @CacheEvict(cacheNames = "job", key = "#id")
    public Job approve(long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không có job " + id));
        job.approve();
        return job;
    }
}
