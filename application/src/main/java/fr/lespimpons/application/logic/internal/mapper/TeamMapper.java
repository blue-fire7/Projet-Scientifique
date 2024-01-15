package fr.lespimpons.application.logic.internal.mapper;

import fr.lespimpons.application.logic.dto.TeamDto;
import fr.lespimpons.application.logic.internal.entity.Team;

public class TeamMapper {


    public static TeamDto toDto(Team team) {
        return new TeamDto(team.getId(), team.getName());
    }
}
