package service;

import dao.CategoryDao;
import model.Category;

import java.util.ArrayList;

public class CategoryService {

    private final CategoryDao categoryDao = new CategoryDao();

    // 1. Lấy tất cả danh mục
    public ArrayList<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    // 2. Tìm danh mục theo ID
    public Category getCategoryById(long id) {
        if (id <= 0) {
            System.out.println("ID danh muc khong hop le");
            return null;
        }

        return categoryDao.findById(id);
    }

    // 3. Thêm danh mục
    public boolean createCategory(String name, String slug) {
        if (name == null || name.isBlank()) {
            System.out.println("Ten danh muc khong duoc de trong");
            return false;
        }

        if (slug == null || slug.isBlank()) {
            System.out.println("Slug khong duoc de trong");
            return false;
        }

        Category category = new Category(null, name.trim(), slug.trim());

        return categoryDao.insert(category);
    }

    // 4. Xóa danh mục
    public boolean deleteCategory(long id) {
        if (id <= 0) {
            System.out.println("ID danh muc khong hop le");
            return false;
        }

        return categoryDao.delete(id);
    }
}