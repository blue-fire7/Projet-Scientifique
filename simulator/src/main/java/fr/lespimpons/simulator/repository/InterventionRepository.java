package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.InterventionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, InterventionId> {
    List<Intervention> findInterventionsByFire_Id(Long fireId);
}
