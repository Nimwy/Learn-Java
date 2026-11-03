package com.example.remoteit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.remoteit.model.Job;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class JobRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    private JobRepository jobRepository;

    @Test
    void findByStatus_returnsOnlyMatchingJobs() {
        jobRepository.save(new Job("Java Developer", "RemoteIT", "Remote", "OPEN"));
        jobRepository.save(new Job("React Developer", "Other", "Hanoi", "CLOSED"));

        List<Job> jobs = jobRepository.findByStatus("OPEN");

        assertThat(jobs).hasSize(1);
        assertThat(jobs.get(0).getTitle()).isEqualTo("Java Developer");
    }
}
