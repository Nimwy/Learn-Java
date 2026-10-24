package com.example.remoteit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Giữ nguyên như bài 5
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;

    public Category() {}

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    // Spring dùng các getter này để chuyển object thành JSON
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
}