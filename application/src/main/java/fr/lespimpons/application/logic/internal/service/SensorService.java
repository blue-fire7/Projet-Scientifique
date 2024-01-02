package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepository;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class SensorService {


    private static SensorService sensorService;
    private final SensorImplRepository sensorImplRepository;

    private SensorService() {
        this.sensorImplRepository = SensorImplRepositoryImpl.getInstance();
    }

    public static SensorService getInstance() {
        return Objects.requireNonNullElseGet(sensorService, SensorService::new);
    }

    public List<SensorDto> getAllSensor() {
        return sensorImplRepository.findAllDto();
    }
}
