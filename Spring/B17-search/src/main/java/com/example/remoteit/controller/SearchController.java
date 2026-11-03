package com.example.remoteit.controller;

import com.example.remoteit.dto.JobResult;
import com.example.remoteit.service.SearchService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search/postgres")
    public List<JobResult> searchPostgres(@RequestParam(defaultValue = "") String q) {
        return searchService.searchPostgres(q);
    }

    @GetMapping("/search/elastic")
    public List<JobResult> searchElastic(@RequestParam(defaultValue = "") String q) {
        return searchService.searchElastic(q);
    }

    @PostMapping("/reindex")
    public Map<String, Object> reindex() {
        return Map.of("indexed", searchService.reindex());
    }
}
