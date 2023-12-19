package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
