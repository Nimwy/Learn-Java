package com.example.remoteit;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

// Tầng nghiệp vụ: Controller không gọi thẳng Repository nữa mà đi qua đây.
// Các quy tắc (kiểm tra trùng, phân quyền, gửi thông báo...) sẽ nằm ở tầng này.
@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> findBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public Category create(Category category) {
        return repository.save(category);
    }
}