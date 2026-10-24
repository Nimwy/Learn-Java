package com.example.remoteit;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCategorySlug(String slug);

    List<Job> findByTagsSlug(String slug);
}