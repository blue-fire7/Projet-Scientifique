package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.entity.SensorEventId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SensorEventRepository extends JpaRepository<SensorEvent, SensorEventId> {
    @Query(value = "SELECT DISTINCT se.sensor_id FROM sensor_event se WHERE se.fire_id IN :fireIds", nativeQuery = true)
    List<Long> findSensorsByFireIds(@Param("fireIds") List<Long> fireIds);

    SensorEvent findSensorEventBySensorId(Long sensorId);
}
