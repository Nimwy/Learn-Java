package com.example.remoteit;


public class Job {
    private final Long id;
    private final String title;
    private final String postedBy; 
    private String status;         

    public Job(Long id, String title, String postedBy, String status) {
        this.id = id;
        this.title = title;
        this.postedBy = postedBy;
        this.status = status;
    }

    public void approve() {
        this.status = "approved";
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getPostedBy() { return postedBy; }
    public String getStatus() { return status; }
}