package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireTruck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireTruckRepository extends JpaRepository<FireTruck, Integer> {
}