package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.entity.SensorEventId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorEventRepository extends JpaRepository<SensorEvent, SensorEventId> {
}
