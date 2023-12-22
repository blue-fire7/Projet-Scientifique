package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FireImplRepository extends JpaRepository<FireImpl, Long> {


    @Query(value = """
    SELECT se.fireImpl
    FROM SensorEvent se
    WHERE se.sensorImpl.id = :sensorId
    ORDER BY se.id.updateAt DESC
""")
    FireImpl findLastFireBySensorId(Long sensorId);
}