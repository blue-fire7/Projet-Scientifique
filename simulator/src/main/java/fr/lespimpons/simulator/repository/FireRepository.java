package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.Fire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface FireRepository extends JpaRepository<Fire, Long> {
}
