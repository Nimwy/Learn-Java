package com.example.remoteit.scheduler;

import com.example.remoteit.model.Job;
import com.example.remoteit.service.JobService;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PendingJobScheduler {

    private static final Logger log = LoggerFactory.getLogger(PendingJobScheduler.class);
    private final JobService jobService;
    private final Set<Long> reminded = new HashSet<>(); 

    public PendingJobScheduler(JobService jobService) {
        this.jobService = jobService;
    }

    @Scheduled(initialDelay = 5_000, fixedRate = 10_000)
    public void remindStalePendingJobs() {
        for (Job job : jobService.findPendingOlderThan(Duration.ofSeconds(15))) {
            if (reminded.add(job.getId())) {
                log.warn("[Scheduled] Nhắc admin: job {} '{}' của {} chờ duyệt quá 15 giây",
                        job.getId(), job.getTitle(), job.getPostedBy());
            }
        }
    }
}
