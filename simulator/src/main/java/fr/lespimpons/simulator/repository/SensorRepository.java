package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
