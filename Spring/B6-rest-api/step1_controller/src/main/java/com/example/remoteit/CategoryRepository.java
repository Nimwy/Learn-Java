package com.example.remoteit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Optional: có thể có, có thể không (slug không tồn tại)
    Optional<Category> findBySlug(String slug);
}