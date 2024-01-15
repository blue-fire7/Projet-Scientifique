package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.TeamDto;
import fr.lespimpons.application.logic.internal.mapper.TeamMapper;
import fr.lespimpons.application.logic.internal.repository.TeamRepositoryImpl;

import java.util.List;

public class TeamService {

    private static TeamService instance;

    private final TeamRepositoryImpl teamRepository = TeamRepositoryImpl.getInstance();

    private TeamService() {
    }

    public static TeamService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (TeamService.class) {
            if (instance == null) {
                instance = new TeamService();
            }
        }
        return instance;
    }

    public List<TeamDto> getAvailableTeamByStationId(Long stationId) {
        return teamRepository.getAvailableTeamByStationId(stationId).stream().map(TeamMapper::toDto).toList();
    }

    public List<TeamDto> getTeamByStationId(Long stationId) {
        return teamRepository.getTeamByStationId(stationId).stream().map(TeamMapper::toDto).toList();
    }


}
