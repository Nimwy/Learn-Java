package com.example.remoteit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Job() {}

    public Job(String title, Category category) {
        this.title = title;
        this.category = category;
    }

    public Long getId() {   return id;  }
    public String getTitle() { return title; }
    public Category getCategory() { return category; }
    
}