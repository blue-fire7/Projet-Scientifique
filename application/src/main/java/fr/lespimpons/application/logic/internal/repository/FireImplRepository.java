package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireImplRepository extends JpaRepository<FireImpl, Long> {
}