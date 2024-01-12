package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.SensorEventImpl;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SensorEventRepositoryImpl extends Repository<SensorEventImpl, Long> implements SensorEventRepository {

    private static SensorEventRepositoryImpl instance;


    private SensorEventRepositoryImpl() {
        super();
    }

    public static SensorEventRepositoryImpl getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (SensorEventRepositoryImpl.class) {
            if (instance == null) {
                instance = new SensorEventRepositoryImpl();
            }
        }
        return instance;
    }


    @Override
    public List<SensorEventImpl> findAllInAreaWithLevel(double longitude, double latitude, double radius) {
        Query nativeQuery = entityManager.createNativeQuery("""
                SELECT
                se.*
                from sensor s
                         left join sensor_event se on s.id = se.sensor_id
                         left join fire f on se.fire_id = f.id
                where se.update_at = (select max(se2.update_at)
                                        from sensor_event se2
                                        where se2.sensor_id = s.id)
                  and ST_DWithin(ST_MakePoint(s.longitude, s.latitude), ST_MakePoint(:longitude, :latitude), :radius )
                  and se.level > 0
                  and f.ended_at is null
                        """, SensorEventImpl.class);
        nativeQuery.setParameter("longitude", longitude);
        nativeQuery.setParameter("latitude", latitude);
        nativeQuery.setParameter("radius", radius);
        return nativeQuery.getResultList();
    }

    public SensorEventImpl findLastSensorEventBySensorId(Long sensorId) {
        TypedQuery<SensorEventImpl> nativeQuery = entityManager.createQuery("""
                SELECT se
                from SensorEventImpl se
                where se.sensorImpl.id = :sensorId
                order by se.id.updateAt desc
                LIMIT 1
                """, SensorEventImpl.class);
        nativeQuery.setParameter("sensorId", sensorId);
        return nativeQuery.getSingleResult();
    }

}
