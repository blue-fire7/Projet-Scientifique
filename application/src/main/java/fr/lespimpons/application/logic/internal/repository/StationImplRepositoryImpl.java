package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.StationImpl;

import java.util.List;

public class StationImplRepositoryImpl extends Repository<StationImpl> implements StationImplRepository {
    @Override
    public List<StationImpl> findWithDispo() {
        List<StationImpl> resultList = entityManager.createNativeQuery("""
                            SELECT distinct fs.*
                            FROM fire_station fs
                                     LEFT JOIN fire_truck ft ON fs.id = ft.fire_station_id
                                     LEFT JOIN fire_fighter ff ON fs.id = ff.fire_station_id
                                     LEFT JOIN team t ON ff.team_id = t.id
                            WHERE NOT EXISTS (
                                SELECT 1 FROM intervention i
                                                  JOIN fire f ON i.fire_id = f.id
                                WHERE (i.fire_truck_id = ft.id OR i.team_id = t.id) AND f.ended_at IS NULL
                            )
                            GROUP BY fs.id;
                """, StationImpl.class).getResultList();
        return resultList;
    }
}
