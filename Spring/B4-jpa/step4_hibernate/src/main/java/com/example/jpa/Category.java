package com.example.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity                  // giống @Table tự chế ở bước 3: "class này ứng với một bảng"
@Table(name = "category")
public class Category {

    @Id                                                 // khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB tự sinh id (BIGSERIAL)
    private Long id;

    private String name;
    private String slug;

    public Category() {} // JPA cần constructor rỗng, giống MiniOrm ở bước 3

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }

    @Override
    public String toString() {
        return "Category{id=" + id + ", name=" + name + ", slug=" + slug + "}";
    }
}
