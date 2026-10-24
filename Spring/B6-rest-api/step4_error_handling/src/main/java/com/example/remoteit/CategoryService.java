package com.example.remoteit;

import java.util.ArrayList;
import java.util.List;
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

    // Không còn trả Optional: không thấy thì NÉM LỖI, handler lo phần trả 404
    public CategoryResponse findBySlug(String slug) {
        Category category = repository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục: " + slug));
        return CategoryResponse.from(category);
    }

    public CategoryResponse create(CategoryRequest request) {
        // Quy tắc nghiệp vụ: slug không được trùng → 409
        if (repository.existsBySlug(request.slug())) {
            throw new ConflictException("Slug đã tồn tại: " + request.slug());
        }
        Category saved = repository.save(new Category(request.name(), request.slug()));
        return CategoryResponse.from(saved);
    }
}