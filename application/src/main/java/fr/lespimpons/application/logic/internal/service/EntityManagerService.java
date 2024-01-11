package fr.lespimpons.application.logic.internal.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerService {


    private static EntityManagerService instance;

    private EntityManagerFactory emf;

    private EntityManagerService() {
    }

    public static synchronized EntityManagerService getInstance() {
        if (instance == null) {
            instance = new EntityManagerService();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("fr.lespimpons.application.logic.internal.entity");
        }
        return emf.createEntityManager();
    }


}
