package com.example.remoteit.dto;

import com.example.remoteit.document.JobDocument;
import com.example.remoteit.model.Job;

public record JobResult(
        Long id,
        String title,
        String company,
        String location,
        String description
) {
    public static JobResult from(Job job) {
        return new JobResult(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getDescription());
    }

    public static JobResult from(JobDocument document) {
        return new JobResult(
                document.getId(),
                document.getTitle(),
                document.getCompany(),
                document.getLocation(),
                document.getDescription());
    }
}
