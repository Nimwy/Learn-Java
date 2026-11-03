package com.example.remoteit.dto;

import com.example.remoteit.model.Job;
import java.time.Instant;

public record JobResponse(
        Long id,
        String title,
        String company,
        String location,
        String status,
        Instant createdAt
) {
    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getStatus(),
                job.getCreatedAt());
    }
}
