package com.example.jpa;

import jakarta.persistence.EntityManager;
import java.lang.reflect.Proxy;

public class MiniRepositoryFactory {

    public static Object create(
            Class<?> repositoryInterface,
            Class<?> entityClass,
            EntityManager em) {
        
        return Proxy.newProxyInstance(
                repositoryInterface.getClassLoader(),
                new Class<?>[] { repositoryInterface },
                new RepositoryHandler(em, entityClass)
        );
    }
}