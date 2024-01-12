package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.SensorEventImpl;

import java.util.List;

public interface SensorEventRepository {

    /*    @Query(value = """
                SELECT
                se.*
                from sensor s
                         left join sensor_event se on s.id = se.sensor_id
                         left join fire f on se.fire_id = f.id
                where se.update_at = (select max(se2.update_at)
                                        from sensor_event se2
                                        where se2.sensor_id = s.id)
                  and ST_DWithin(ST_MakePoint(s.longitude, s.latitude), ST_MakePoint(:longitude, :latitude), :radius )
                  and se.level > 0
                  and f.ended_at is null
                            """, nativeQuery = true)*/
    List<SensorEventImpl> findAllInAreaWithLevel(double longitude, double latitude, double radius);

}