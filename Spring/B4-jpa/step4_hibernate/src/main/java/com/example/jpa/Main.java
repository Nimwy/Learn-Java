package com.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static final Logger HIBERNATE_LOG =
            Logger.getLogger("org.hibernate");

    public static void main(String[] args) {
        HIBERNATE_LOG.setLevel(Level.SEVERE);

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("remoteit");

        EntityManager em = emf.createEntityManager();

        try {
            // 1. Lưu dữ liệu
            em.getTransaction().begin();

            em.persist(new Category("Backend", "backend"));
            em.persist(new Category("Frontend", "frontend"));

            em.getTransaction().commit();

            // 2. Tìm theo ID
            Category category = em.find(Category.class, 2L);
            System.out.println("find(2): " + category);

            // 3. Lấy tất cả Category bằng JPQL
            List<Category> categories = em.createQuery(
                    "SELECT c FROM Category c",
                    Category.class
            ).getResultList();

            for (Category c : categories) {
                System.out.println(c);
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}