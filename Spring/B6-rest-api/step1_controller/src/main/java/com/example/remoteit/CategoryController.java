package com.example.remoteit;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                     // class này nhận HTTP request, trả về JSON
@RequestMapping("/api/categories")  // mọi URL trong class bắt đầu bằng /api/categories
public class CategoryController {
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) { // Spring tiêm vào (bài 3)
        this.repository = repository;
    }

    // GET /api/categories → danh sách, Spring tự chuyển List<Category> thành JSON
    @GetMapping
    public List<Category> findAll() {
        return repository.findAll();
    }

    // GET /api/categories/backend → {slug} lấy từ URL
    @GetMapping("/{slug}")
    public ResponseEntity<Category> findBySlug(@PathVariable String slug) {
        Optional<Category> category = repository.findBySlug(slug);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(category.get());     // 200 OK + JSON
    }
}