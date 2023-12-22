package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.InterventionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, InterventionId> {
    @Query(value = "SELECT DISTINCT id FROM fire WHERE fire.id NOT IN (SELECT DISTINCT fire_id FROM Intervention)", nativeQuery = true)
    List<Long> findFireIdsWithoutIntervention();
}
