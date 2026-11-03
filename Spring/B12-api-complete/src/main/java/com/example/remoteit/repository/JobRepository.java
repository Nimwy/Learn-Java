package com.example.remoteit.repository;

import com.example.remoteit.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

    
    @Query("""
            select j from Job j
            where (:status is null or j.status = :status)
              and (:location is null or j.location = :location)
              and (:keyword is null or lower(j.title) like :keyword)
            """)
    Page<Job> search(
            @Param("status") String status,
            @Param("location") String location,
            @Param("keyword") String keyword,
            Pageable pageable);
}
