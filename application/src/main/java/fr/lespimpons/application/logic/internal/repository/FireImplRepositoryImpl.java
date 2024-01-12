package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireImpl;
import jakarta.persistence.TypedQuery;

public class FireImplRepositoryImpl extends Repository<FireImpl, Long> implements FireImplRepository {


    private static FireImplRepositoryImpl instance;

    public static FireImplRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new FireImplRepositoryImpl();
        }
        return instance;
    }

    public FireImpl findLastFireBySensorId(Long SensorId) {


        TypedQuery<FireImpl> q = entityManager.createQuery("""
                    SELECT se.fireImpl
                    FROM SensorEventImpl se
                    WHERE se.sensorImpl.id = :sensorId
                    ORDER BY se.id.updateAt DESC
                    LIMIT 1
                """, FireImpl.class);
        q.setParameter("sensorId", SensorId);
        return q.getSingleResult();
    }

}
