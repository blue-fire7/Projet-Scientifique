package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FireRepository extends JpaRepository<Fire, Long> {
}
