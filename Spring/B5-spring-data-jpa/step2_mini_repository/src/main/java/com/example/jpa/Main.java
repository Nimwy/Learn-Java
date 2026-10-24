package com.example.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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

        CategoryRepository repository = (CategoryRepository)
                MiniRepositoryFactory.create(
                        CategoryRepository.class,
                        Category.class,
                        em
                );

        repository.save(new Category("Backend", "backend"));
        repository.save(new Category("Frontend", "frontend"));
        repository.save(new Category("Mobile", "mobile"));

        System.out.println("findById(2): "
                + repository.findById(2L));

        System.out.println("findAll: "
                + repository.findAll());

        System.out.println("findBySlug: "
                + repository.findBySlug("mobile"));

        System.out.println("findByNameContaining(\"end\"): "
                + repository.findByNameContaining("end"));

        System.out.println("Class thật của repository: "
                + repository.getClass().getName());

        em.close();
        emf.close();
    }
}