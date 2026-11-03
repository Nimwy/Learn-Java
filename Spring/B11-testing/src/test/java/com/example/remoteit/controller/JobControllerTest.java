package com.example.remoteit.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.remoteit.dto.JobResponse;
import com.example.remoteit.service.JobService;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(JobController.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @Test
    void findById_returnsJson() throws Exception {
        when(jobService.findById(1L)).thenReturn(new JobResponse(
                1L,
                "Java Developer",
                "RemoteIT",
                "Remote",
                "OPEN",
                Instant.parse("2026-01-01T00:00:00Z")));

        mockMvc.perform(get("/api/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Developer"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }
}
