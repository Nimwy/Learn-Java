package com.example.remoteit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "jobs")
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "posted_by", nullable = false)
    private String postedBy; 

    @Column(nullable = false, length = 20)
    private String status; 
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Job() {
    }

    public Job(String title, String postedBy, String status) {
        this.title = title;
        this.postedBy = postedBy;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public void approve() {
        this.status = "approved";
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getPostedBy() { return postedBy; }
    public Instant getCreatedAt() { return createdAt; }
    public String getStatus() { return status; }
}
