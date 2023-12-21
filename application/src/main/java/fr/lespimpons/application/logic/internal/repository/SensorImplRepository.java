package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.entity.SensorImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorImplRepository extends JpaRepository<SensorImpl, Long> {
    @Query(value = """
        SELECT  new fr.lespimpons.application.logic.dto.SensorDto(
        s.id,
        s.longitude,
        s.latitude,
        se.level
    )
        FROM SensorImpl s
        LEFT JOIN SensorEvent se ON s = se.sensor
        WHERE se.id.updateAt = (SELECT MAX(se2.id.updateAt)
        FROM SensorEvent se2
        WHERE se2.sensor = s)
        OR se.level IS NULL
""")
    List<SensorDto> findAllDto();

}