package com.example.remoteit.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class JobMessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(JobMessageConsumer.class);

    @RabbitListener(queues = RabbitConfig.JOB_QUEUE)
    public void consume(String message) {
        String[] parts = message.split("\\|", 4);
        if (parts.length == 4 && "ADMIN_NOTIFY".equals(parts[0])) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            log.info("[RabbitMQ] Notification service đã báo admin: job {} '{}' của {}",
                    parts[1], parts[2], parts[3]);
        } else {
            log.warn("[RabbitMQ] Message không hợp lệ: {}", message);
        }
    }
}
