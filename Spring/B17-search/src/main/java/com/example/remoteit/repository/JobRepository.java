package com.example.remoteit.repository;

import com.example.remoteit.model.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query(value = """
            SELECT id, title, company, location, description
            FROM jobs
            WHERE search_vector @@ plainto_tsquery('simple', :q)
            ORDER BY ts_rank(search_vector, plainto_tsquery('simple', :q)) DESC
            LIMIT 20
            """, nativeQuery = true)
    List<Job> fullTextSearch(@Param("q") String q);
}
