package com.example.remoteit.event;


public record JobCreatedEvent(Long jobId, String title, String postedBy) {
}
