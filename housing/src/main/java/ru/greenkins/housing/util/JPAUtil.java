package ru.greenkins.housing.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY;

    static {
        try {
            // Инициализация EntityManagerFactory для подключения к базе данных
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("housingPU");
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Метод для получения EntityManager
    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    // Закрытие EntityManagerFactory при завершении работы
    public static void shutdown() {
        if (ENTITY_MANAGER_FACTORY != null) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }
}