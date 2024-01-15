package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.entity.SensorEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorEventRepository extends JpaRepository<SensorEvent, SensorEventId> {
    @Query(value = "SELECT DISTINCT se.sensor_id FROM sensor_event se WHERE se.fire_id IN :fireIds", nativeQuery = true)
    List<Long> findSensorsByFireIds(@Param("fireIds") List<Long> fireIds);

//    @Query("Select se from SensorEvent se where se.fire.id = :fireId and se. order by se.level DESC limit 1")
    @Query(value= """
        SELECT
            sensor_id,
            fire_id,
            update_at,
            level
        FROM
            sensor_event se
        WHERE
            update_at in((select max(se2.update_at) from sensor_event se2 where se2.sensor_id = se.sensor_id)) and
            fire_id = :fireId
        order by level DESC 
        limit 1;
    """, nativeQuery = true)
    SensorEvent findMaxSensorEventByFireID(@Param("fireId") Long fireId);

    SensorEvent findSensorEventBySensorId(Long sensorId);
}
