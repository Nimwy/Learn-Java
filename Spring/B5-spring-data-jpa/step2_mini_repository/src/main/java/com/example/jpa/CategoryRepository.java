package com.example.jpa;

import java.util.List;

public interface CategoryRepository {
    void save(Category category);

    Category findById(Long id);

    List<Category> findAll();

    List<Category> findBySlug(String slug);

    List<Category> findByNameContaining(String text);
}