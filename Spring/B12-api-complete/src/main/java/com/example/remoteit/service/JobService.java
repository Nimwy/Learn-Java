package com.example.remoteit.service;

import com.example.remoteit.dto.JobResponse;
import com.example.remoteit.dto.PageResponse;
import com.example.remoteit.model.Job;
import com.example.remoteit.repository.JobRepository;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JobService {

    private static final Set<String> SORTABLE = Set.of("createdAt", "title", "id");

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public PageResponse<JobResponse> search(
            String status,
            String location,
            String keyword,
            Pageable pageable) {

        for (Sort.Order order : pageable.getSort()) {
            if (!SORTABLE.contains(order.getProperty())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Không sắp xếp được theo '" + order.getProperty() + "'. Cho phép: " + SORTABLE);
            }
        }

        Page<Job> page = jobRepository.search(
                blankToNull(status),
                blankToNull(location),
                keywordPattern(keyword),
                pageable);

        return PageResponse.from(page.map(JobResponse::from));
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    
    private String keywordPattern(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return "%" + keyword.trim().toLowerCase() + "%";
    }
}
