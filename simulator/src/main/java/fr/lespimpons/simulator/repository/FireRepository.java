package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FireRepository extends JpaRepository<Fire, Long> {
}
