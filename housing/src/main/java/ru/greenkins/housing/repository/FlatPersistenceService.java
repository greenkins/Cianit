package ru.greenkins.housing.repository;

import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class FlatPersistenceService {

    // Метод для синхронизации коллекции с базой данных
    public void syncCollectionWithDatabase(List<Flat> flats) {
        // Сначала сохраняем все объекты в базе данных
        saveCollectionToDatabase(flats);

        // Затем загружаем актуальное состояние из базы данных
        List<Flat> loadedFlats = loadCollectionFromDatabase();

        // Возвращаем актуальное состояние коллекции, если нужно
        // Вы можете перезаписать текущую коллекцию или использовать ее как нужно
        flats.clear();
        flats.addAll(loadedFlats);
    }

    public void saveCollectionToDatabase(List<Flat> flats) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Удаление всех записей перед сохранением (если полная синхронизация)
            em.createQuery("DELETE FROM FlatEntity").executeUpdate();

            // Сохранение текущей коллекции
            for (Flat flat : flats) {
                FlatEntity flatEntity = FlatEntity.fromModel(flat);
                em.persist(flatEntity);
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Failed to save collection to database", e);
        } finally {
            em.close();
        }
    }

    public List<Flat> loadCollectionFromDatabase() {

        try (EntityManager em = JPAUtil.getEntityManager()) {
            List<FlatEntity> entities = em.createQuery("SELECT f FROM FlatEntity f", FlatEntity.class).getResultList();
            return entities.stream()
                    .map(FlatEntity::toModel)
                    .toList();
        }
    }
}
