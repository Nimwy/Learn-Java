package com.example.jpa;

import jakarta.persistence.EntityManager;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RepositoryHandler implements InvocationHandler {
    private EntityManager em;
    private Class<?> entityClass;

    public RepositoryHandler(EntityManager em, Class<?> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        String methodName = method.getName();
        String entity = entityClass.getSimpleName();

        // 1. Lưu một entity
        if (methodName.equals("save")) {
            em.getTransaction().begin();
            em.persist(args[0]);
            em.getTransaction().commit();
            return null;
        }

        // 2. Tìm entity theo khóa chính
        if (methodName.equals("findById")) {
            return em.find(entityClass, args[0]);
        }

        // 3. Lấy tất cả entity
        if (methodName.equals("findAll")) {
            String jpql = "SELECT e FROM " + entity + " e";

            System.out.println("JPQL tự sinh: " + jpql);

            return em.createQuery(jpql).getResultList();
        }

        // 4. Xử lý các hàm bắt đầu bằng findBy
        if (methodName.startsWith("findBy")) {
            String condition =
                    methodName.substring("findBy".length());

            Object value = args[0];
            String jpql;

            if (condition.endsWith("Containing")) {
                String field = toFieldName(
                        condition.replace("Containing", "")
                );

                jpql = "SELECT e FROM " + entity
                        + " e WHERE e." + field + " LIKE :value";

                value = "%" + value + "%";
            } else {
                String field = toFieldName(condition);

                jpql = "SELECT e FROM " + entity
                        + " e WHERE e." + field + " = :value";
            }

            System.out.println("JPQL tự sinh: " + jpql);

            return em.createQuery(jpql)
                    .setParameter("value", value)
                    .getResultList();
        }

        throw new UnsupportedOperationException(
                "MiniRepository chưa hỗ trợ hàm: " + methodName
        );
    }

    private String toFieldName(String text) {
        return Character.toLowerCase(text.charAt(0))
                + text.substring(1);
    }
}