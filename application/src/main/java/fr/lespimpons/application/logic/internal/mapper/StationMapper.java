package fr.lespimpons.application.logic.internal.mapper;

import fr.lespimpons.application.logic.dto.StationDto;
import fr.lespimpons.application.logic.internal.entity.StationImpl;

public class StationMapper {


    public static StationDto toDto(StationImpl station) {
        return new StationDto(station.getId(), station.getLongitude(), station.getLatitude());
    }
}
