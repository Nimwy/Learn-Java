package com.example.remoteit.service;

import com.example.remoteit.model.Job;


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobService {
    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private final List<Job> jobs = new ArrayList<>();
    private long nextId = 1;

    public JobService() {
        jobs.add(new Job(nextId++, "Java Developer", "hr@remoteit.vn", "pending"));
    }

    public List<Job> findAll() {
        log.info("Lấy danh sách job, hiện có {} tin", jobs.size());
        return jobs;
    }

    @Cacheable(cacheNames = "job", key = "#id")
    public Job findById(long id) {
        log.info("Cache MISS: đọc job {} từ nguồn chậm (giả lập 700ms)", id);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return jobs.stream()
                .filter(job -> job.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không có job " + id));
    }

    public synchronized Job create(String title, String postedBy) {
        Job job = new Job(nextId++, title, postedBy, "pending");
        jobs.add(job);
        return job;
    }
    @CacheEvict(cacheNames = "job", key = "#id")
    public Job approve(long id) {
        for (Job job : jobs) {
            if (job.getId() == id) {
                job.approve();
                return job;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không có job " + id);
    }
}
