package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.InterventionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, InterventionId> {
    List<Intervention> findInterventionsByFire_Id(Long fireId);

    Intervention findFirstByFireTruck_Id(Long fireTruckId);

    @Query("Select i from Intervention i where i.fire.endedAt is null")
    List<Intervention> findActiveInterventions();
}
