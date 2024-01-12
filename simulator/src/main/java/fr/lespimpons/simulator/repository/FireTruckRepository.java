package fr.lespimpons.simulator.repository;

import fr.lespimpons.simulator.entity.FireTruck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface FireTruckRepository extends JpaRepository<FireTruck, Long> {

    @Query(
            "SELECT ft from FireTruck ft " +
                    "join Intervention i on ft.id = i.fireTruck.id " +
                    "join Fire f on f.id = i.fire.id " +
                    "where f.endedAt is null"
    )
    public List<FireTruck> findFireTruckInIntervention();
}
