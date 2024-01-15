package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.Team;

import java.util.List;

public interface TeamRepository {


    //TODO: pas bon
/*    @Query(value = """
    SELECT t
    FROM Team t
    WHERE EXISTS (
        SELECT 1 FROM FireFighter ff
        WHERE ff.team.id = t.id AND ff.fireStation.id = :stationId
    )
    AND NOT EXISTS (
        SELECT 1 FROM Intervention i
        JOIN FireImpl f ON i.fire = f
        WHERE i.team.id = t.id AND f.endedAt IS NULL
    )
""")*/
    List<Team> getAvailableTeamByStationId(Long stationId);
}