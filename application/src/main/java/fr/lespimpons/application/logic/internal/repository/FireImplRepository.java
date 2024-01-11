package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.FireImpl;
import org.springframework.stereotype.Repository;

@Repository
public interface FireImplRepository {


    /*    @Query(value = """
        SELECT se.fireImpl
        FROM SensorEvent se
        WHERE se.sensorImpl.id = :sensorId
        ORDER BY se.id.updateAt DESC
    """)*/
    FireImpl findLastFireBySensorId(Long sensorId);
}