package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.service.EntityManagerService;
import jakarta.persistence.EntityManager;

public abstract class Repository<T> {


    protected final EntityManager entityManager;

    public Repository() {
        this.entityManager = EntityManagerService.getInstance().getEntityManager();
    }

    public void save(T object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public void update(T object) {
        entityManager.getTransaction().begin();
        entityManager.merge(object);
        entityManager.getTransaction().commit();
    }

    public void delete(T object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }

    public T findById(Class<T> clazz, Long id) {
        return entityManager.find(clazz, id);
    }

    public void deleteById(Class<T> clazz, Long id) {
        T object = findById(clazz, id);
        delete(object);
    }

}
