package fr.lespimpons.application.logic.internal.repository;

import fr.lespimpons.application.logic.internal.entity.Team;

import java.util.List;

public class TeamRepositoryImpl extends Repository<Team> implements TeamRepository{

    //TODO : pas bon
    @Override
    public List<Team> findTeamDispoByStationId(Long stationId) {
        return entityManager.createQuery("""
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
                        """, Team.class).setParameter("stationId", stationId).getResultList();
    }
}
