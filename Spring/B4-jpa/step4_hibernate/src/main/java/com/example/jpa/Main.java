package com.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    // Tắt bớt log của Hibernate cho dễ nhìn kết quả (không bắt buộc)
    static final Logger HIBERNATE_LOG = Logger.getLogger("org.hibernate");

    public static void main(String[] args) {
        HIBERNATE_LOG.setLevel(Level.SEVERE);

        // Đọc cấu hình "remoteit" trong META-INF/persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("remoteit");

        // EntityManager: thay cho MiniOrm ở bước 3
        EntityManager em = emf.createEntityManager();

        // --- Lưu: phải mở transaction khi thay đổi dữ liệu ---
        em.getTransaction().begin();
        em.persist(new Category("Backend", "backend"));
        em.persist(new Category("Frontend", "frontend"));
        em.getTransaction().commit();

        // --- Tìm theo id ---
        Category category = em.find(Category.class, 2L);
        System.out.println("find(2): " + category);

        // --- Lấy tất cả: viết JPQL, truy vấn theo TÊN CLASS chứ không phải tên bảng ---
        List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        for (Category c : categories) {
            System.out.println(c);
        }

        em.close();
        emf.close();
    }
}
