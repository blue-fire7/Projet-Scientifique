package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.entity.SensorImpl;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SensorImplRepositoryImpl extends Repository<SensorImpl, Long> implements SensorImplRepository {


    private static SensorImplRepositoryImpl sensorImplRepository;

    @Override
    public List<SensorDto> findAllDto() {
        TypedQuery<SensorDto> q = entityManager.createQuery("""
                    SELECT  new fr.lespimpons.application.logic.dto.SensorDto(
                    s.id,
                    s.longitude,
                    s.latitude,
                    se.level
                )
                    FROM SensorImpl s
                    LEFT JOIN SensorEvent se ON s = se.sensorImpl
                    WHERE se.id.updateAt = (SELECT MAX(se2.id.updateAt)
                    FROM SensorEvent se2
                    WHERE se2.sensorImpl = s)
                    OR se.level IS NULL
                    """, SensorDto.class);
        return q.getResultList();
    }

    @Override
    public List<SensorImpl> findAllSensorByFireId(Long fireId) {
        TypedQuery<SensorImpl> q = entityManager.createQuery("""
                    SELECT se.sensorImpl
                FROM SensorEvent se
                WHERE se.fireImpl.id = :fireId
                    """, SensorImpl.class);
        return q.getResultList();
    }

    @Override
    public Integer lastLevelBySensorId(Long sensorId) {
        return entityManager.createQuery("""
                                SELECT se.level
                                FROM SensorEvent se
                                WHERE se.sensorImpl.id = :sensorId
                                ORDER BY se.id.updateAt DESC
                                LIMIT 1
                """, Integer.class).setParameter("sensorId", sensorId).getSingleResult();
    }


    public static SensorImplRepositoryImpl getInstance() {
        if (sensorImplRepository == null) {
            sensorImplRepository = new SensorImplRepositoryImpl();
        }
        return sensorImplRepository;
    }
}
