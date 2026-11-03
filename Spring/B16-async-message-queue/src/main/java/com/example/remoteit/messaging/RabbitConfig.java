package com.example.remoteit.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String JOB_QUEUE = "job.created";

    
    @Bean
    public Queue jobCreatedQueue() {
        return new Queue(JOB_QUEUE, true); 
    }
}
