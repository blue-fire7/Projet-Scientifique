package fr.lespimpons.application.logic.internal.mapper;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.entity.SensorImpl;

public class SensorMapper {


    public static SensorDto toDto(SensorImpl sensorImpl, int intensity) {
        return new SensorDto(sensorImpl.getId(), sensorImpl.getLongitude(), sensorImpl.getLatitude(), intensity);
    }
}
