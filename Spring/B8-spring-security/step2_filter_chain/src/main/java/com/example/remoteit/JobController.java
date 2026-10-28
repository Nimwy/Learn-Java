package com.example.remoteit;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    
    @GetMapping
    public List<Job> findAll() {
        return service.findAll();
    }

    
    @PostMapping
    public ResponseEntity<Job> create(@RequestBody JobRequest request, Principal principal) {
        Job job = service.create(request.title(), principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    
    @PutMapping("/{id}/approve")
    public Job approve(@PathVariable long id) {
        return service.approve(id);
    }
}