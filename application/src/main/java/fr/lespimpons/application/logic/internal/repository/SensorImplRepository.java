package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.entity.SensorImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorImplRepository {
    /*    @Query(value = """
                        SELECT  new fr.lespimpons.application.logic.dto.SensorDto(
                        s.id,
                        s.longitude,
                        s.latitude,
                        se.level
                    )
                        FROM SensorImpl s
                        LEFT JOIN SensorEvent se ON s = se.sensorImpl
                        WHERE se.id.updateAt = (SELECT MAX(se2.id.updateAt)
                        FROM SensorEvent se2
                        WHERE se2.sensorImpl = s)
                        OR se.level IS NULL
                """)*/
    List<SensorDto> findAllDto();


    /*    @Query(value = """
                    SELECT se.sensorImpl
                    FROM SensorEvent se
                    WHERE se.fireImpl.id = :fireId
                """)*/
    List<SensorImpl> findAllSensorByFireId(Long fireId);


    /*    @Query(value = """
                    SELECT se.level
                    FROM SensorEvent se
                    WHERE se.sensorImpl.id = :sensorId
                    ORDER BY se.id.updateAt DESC
                    LIMIT 1
                """)*/
    Integer lastLevelBySensorId(Long sensorId);

}