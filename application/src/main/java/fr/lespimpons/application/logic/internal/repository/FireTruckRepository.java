package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireTruck;

import java.util.List;

public interface FireTruckRepository {



    /*    @Query(value = """
                        SELECT ft
                        from FireTruck ft
    
                        where ft.fireStation.id = :stationId
                        AND ft.id NOT IN (
                        SELECT i.fireTruck.id FROM Intervention i WHERE i.fireTruck.fireStation.id = :stationId AND i.fire.endedAt IS NULL
                    )
                        """)*/
    List<FireTruck> findFireTruckDispoByStationId(Long stationId);
}