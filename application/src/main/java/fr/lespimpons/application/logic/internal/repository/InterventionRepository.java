package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.Intervention;
import fr.lespimpons.application.logic.internal.entity.InterventionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterventionRepository extends JpaRepository<Intervention, InterventionId> {
}