package com.example.remoteit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryResponse> findAll() {
        List<CategoryResponse> result = new ArrayList<>();

        for (Category category : repository.findAll()) {
            result.add(CategoryResponse.from(category));
        }

        return result;
    }

    public Optional<CategoryResponse> findBySlug(String slug) {
        Optional<Category> category = repository.findBySlug(slug);

        if (category.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(CategoryResponse.from(category.get()));
    }

    public CategoryResponse create(CategoryRequest request) {
        Category saved = repository.save(
                new Category(request.name(), request.slug())
        );

        return CategoryResponse.from(saved);
    }
}