package com.example.remoteit;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;  // đổi từ Repository sang Service

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Category> findBySlug(@PathVariable String slug) {
        Optional<Category> category = service.findBySlug(slug);
        if (category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category.get());
    }

    // POST /api/categories với body JSON {"name": "...", "slug": "..."}
    // @RequestBody: Spring tự chuyển JSON trong body thành object Category
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category saved = service.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201 Created
    }
}