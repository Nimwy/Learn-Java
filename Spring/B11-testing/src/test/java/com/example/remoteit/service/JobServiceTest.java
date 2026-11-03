package com.example.remoteit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.remoteit.dto.JobResponse;
import com.example.remoteit.model.Job;
import com.example.remoteit.repository.JobRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    void findById_returnsJob() {
        Job job = new Job("Java Developer", "RemoteIT", "Remote", "OPEN");
        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        JobResponse response = jobService.findById(1L);

        assertThat(response.title()).isEqualTo("Java Developer");
        assertThat(response.status()).isEqualTo("OPEN");
        verify(jobRepository).findById(1L);
    }

    @Test
    void findById_notFound_throws404() {
        when(jobRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> jobService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(error -> assertThat(
                        ((ResponseStatusException) error).getStatusCode())
                        .isEqualTo(HttpStatus.NOT_FOUND));
    }
}
