package com.example.remoteit.repository;

import com.example.remoteit.document.JobDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobSearchRepository extends ElasticsearchRepository<JobDocument, Long> {
}
