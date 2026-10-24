package com.example.remoteit;

// DTO cho dữ liệu TRẢ VỀ: ta tự chọn field nào được lộ ra ngoài API.
public record CategoryResponse(Long id, String name, String slug) {

    // Chuyển entity thành DTO
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getSlug());
    }
}