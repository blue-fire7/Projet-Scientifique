package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.service.EntityManagerService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class Repository<T, ID> {

    protected final EntityManager entityManager;

    private final Class<T> clazz;

    public Repository() {
        this.clazz = reflectClassType();
        this.entityManager = EntityManagerService.getInstance().getEntityManager();
    }

    @Transactional
    public T saveAndFlush(T object) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        object = this.save(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
    }

    @Transactional
    public T save(T object) {
        if (this.isPersisted(object)) {
            entityManager.persist(object);
            return object;
        }
        return entityManager.merge(object);
    }


    @Transactional
    public void update(T object) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.merge(object);
        entityManager.getTransaction().commit();
    }

    @Transactional
    public void delete(T object) {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }

    @Transactional
    public List<T> findAll() {
        return entityManager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz).getResultList();
    }

    @Transactional
    public T findById(ID id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    public void deleteById(ID id) {
        T object = findById(id);
        delete(object);
    }


    @SuppressWarnings("unchecked")
    private Class<T> reflectClassType() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private boolean isPersisted(T object) {
        return entityManager.contains(object);
    }

}
