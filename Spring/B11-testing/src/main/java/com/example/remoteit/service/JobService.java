package com.example.remoteit.service;

import com.example.remoteit.dto.JobResponse;
import com.example.remoteit.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponse findById(long id) {
        return jobRepository.findById(id)
                .map(JobResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không có job " + id));
    }
}
