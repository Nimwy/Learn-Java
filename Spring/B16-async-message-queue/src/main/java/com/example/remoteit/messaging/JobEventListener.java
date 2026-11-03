package com.example.remoteit.messaging;

import com.example.remoteit.event.JobCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class JobEventListener {

    private static final Logger log = LoggerFactory.getLogger(JobEventListener.class);
    private final RabbitTemplate rabbitTemplate;

    public JobEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(JobCreatedEvent event) {
        log.info("[Async] Bắt đầu gửi email xác nhận cho HR {} (thread {})",
                event.postedBy(), Thread.currentThread().getName());

        try {
            Thread.sleep(1200); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("[Async] Đã gửi email xác nhận job {} '{}' cho HR", event.jobId(), event.title());

        rabbitTemplate.convertAndSend(RabbitConfig.JOB_QUEUE,
                "ADMIN_NOTIFY|" + event.jobId() + "|" + event.title() + "|" + event.postedBy());
        log.info("[Async] Đã đẩy yêu cầu báo admin vào RabbitMQ");
    }
}
