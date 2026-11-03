package com.example.remoteit.repository;

import com.example.remoteit.model.Job;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    
    List<Job> findByStatusAndCreatedAtBefore(String status, Instant threshold);
}
