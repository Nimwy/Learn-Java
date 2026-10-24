package com.example.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getSlug() { return slug; }

    @Override
    public String toString() {
        return "Category{id=" + id
                + ", name=" + name
                + ", slug=" + slug + "}";
    }
}
