package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireTruck;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FireTruckRepositoryImpl extends Repository<FireTruck, Long> implements FireTruckRepository {

    private static FireTruckRepositoryImpl instance;

    public static FireTruckRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new FireTruckRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<FireTruck> findFireTruckDispoByStationId(Long stationId) {
        TypedQuery<FireTruck> q = entityManager.createQuery("""
                    SELECT ft
                    from FireTruck ft
                    where ft.fireStation.id = :stationId
                    AND ft.id NOT IN (
                    SELECT i.fireTruck.id FROM Intervention i WHERE i.fireTruck.fireStation.id = :stationId AND i.fire.endedAt IS NULL
                )
                    """, FireTruck.class);
        q.setParameter("stationId", stationId);
        return q.getResultList();
    }

    public Integer getAvailableFireTruck(Long id) {
        TypedQuery<Long> q = entityManager.createQuery("""
                    SELECT COUNT(ft)
                    from FireTruck ft
                    where ft.fireStation.id = :stationId
                    AND ft.id NOT IN (
                    SELECT i.fireTruck.id FROM Intervention i WHERE i.fireTruck.fireStation.id = :stationId AND i.fire.endedAt IS NULL
                )
                    """, Long.class);
        q.setParameter("stationId", id.intValue());
        return q.getSingleResult().intValue();
    }

    public Integer getFiretruckOfStation(Long id) {
        TypedQuery<Long> q = entityManager.createQuery("""
                SELECT COUNT(ft)
                from FireTruck ft
                where ft.fireStation.id = :stationId
                """, Long.class);
        q.setParameter("stationId", id);
        return q.getSingleResult().intValue();
    }

    public List<FireTruck> getFireTruckOnIntervention() {
        TypedQuery<FireTruck> q = entityManager.createQuery("""
                SELECT ft
                from FireTruck ft
                where ft.id IN (
                SELECT i.fireTruck.id FROM Intervention i WHERE i.fire.endedAt IS NULL
            )
                """, FireTruck.class);
        return q.getResultList();
    }
}
