package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.service.EntityManagerService;
import jakarta.persistence.EntityManager;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class Repository<T, ID> {

    protected final EntityManager entityManager;

    private final Class<T> clazz;

    public Repository() {
        this.clazz = reflectClassType();
        this.entityManager = EntityManagerService.getInstance().getEntityManager();
    }


    public void save(T object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public T saveAndFlush(T object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
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

    public List<T> findAll() {
        return entityManager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz).getResultList();
    }

    public T findById(ID id) {
        return entityManager.find(clazz, id);
    }

    public void deleteById(ID id) {
        T object = findById(id);
        delete(object);
    }


    @SuppressWarnings("unchecked")
    private Class<T> reflectClassType() {
        return ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
