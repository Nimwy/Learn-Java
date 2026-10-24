package com.example.remoteit;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class JobService {
    private final List<Job> jobs = new ArrayList<>();
    private long nextId = 1;

    public JobService() {
        jobs.add(new Job(nextId++, "Java Developer", "hr@remoteit.vn", "approved"));
    }

    public List<Job> findAll() {
        return jobs;
    }

    public synchronized Job create(String title, String postedBy) {
        Job job = new Job(nextId++, title, postedBy, "pending"); 
        jobs.add(job);
        return job;
    }

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