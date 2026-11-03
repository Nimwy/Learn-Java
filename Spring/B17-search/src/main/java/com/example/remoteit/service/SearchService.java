package com.example.remoteit.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import com.example.remoteit.document.JobDocument;
import com.example.remoteit.dto.JobResult;
import com.example.remoteit.repository.JobRepository;
import com.example.remoteit.repository.JobSearchRepository;
import java.util.List;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final JobRepository jobRepository;
    private final JobSearchRepository searchRepository;
    private final ElasticsearchOperations elasticsearch;

    public SearchService(
            JobRepository jobRepository,
            JobSearchRepository searchRepository,
            ElasticsearchOperations elasticsearch) {
        this.jobRepository = jobRepository;
        this.searchRepository = searchRepository;
        this.elasticsearch = elasticsearch;
    }

    public List<JobResult> searchPostgres(String q) {
        if (q == null || q.isBlank()) {
            return List.of();
        }
        return jobRepository.fullTextSearch(q.trim()).stream()
                .map(JobResult::from)
                .toList();
    }

    public List<JobResult> searchElastic(String q) {
        if (q == null || q.isBlank()) {
            return List.of();
        }

        NativeQuery query = NativeQuery.builder()
                .withQuery(queryBuilder -> queryBuilder.match(match -> match
                        .field("searchText")
                        .query(q.trim())
                        .operator(Operator.And)
                        .fuzziness("AUTO")))
                .withMaxResults(20)
                .build();

        return elasticsearch.search(query, JobDocument.class)
                .getSearchHits().stream()
                .map(hit -> JobResult.from(hit.getContent()))
                .toList();
    }

    public int reindex() {
        List<JobDocument> documents = jobRepository.findAll().stream()
                .map(JobDocument::from)
                .toList();

        searchRepository.deleteAll();
        searchRepository.saveAll(documents);
        return documents.size();
    }
}
