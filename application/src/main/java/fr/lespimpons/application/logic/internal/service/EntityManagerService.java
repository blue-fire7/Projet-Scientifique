package fr.lespimpons.application.logic.internal.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerService {


    private static EntityManagerService instance;

    private EntityManagerService(){
    }


    public EntityManager getEntityManager() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("fr.lespimpons.application.logic.internal.entity")) {
            return emf.createEntityManager();
        }

    }



    public static synchronized EntityManagerService getInstance() {
        if (instance == null) {
            instance = new EntityManagerService();
        }
        return instance;
    }


}
