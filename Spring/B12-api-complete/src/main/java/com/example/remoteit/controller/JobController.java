package com.example.remoteit.controller;

import com.example.remoteit.dto.JobResponse;
import com.example.remoteit.dto.PageResponse;
import com.example.remoteit.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Danh sách tin tuyển dụng")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    @Operation(summary = "Danh sách job có phân trang, lọc và sắp xếp")
    public PageResponse<JobResponse> list(
            @RequestParam(required = false)
            @Parameter(description = "OPEN, CLOSED hoặc PENDING")
            String status,

            @RequestParam(required = false)
            @Parameter(description = "Remote, Hanoi hoặc Ho Chi Minh")
            String location,

            @RequestParam(required = false)
            @Parameter(description = "Tìm gần đúng trong title")
            String keyword,

            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return jobService.search(status, location, keyword, pageable);
    }
}
