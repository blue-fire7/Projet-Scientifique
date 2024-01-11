package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepository;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SensorService {

    private static SensorService instance;
    private final SensorImplRepository sensorImplRepository;

    private SensorService() {
        this.sensorImplRepository = SensorImplRepositoryImpl.getInstance();
    }

    public static SensorService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (SensorService.class) {
            if (instance == null) {
                instance = new SensorService();
            }
        }
        return instance;
    }

    public List<SensorDto> getAllSensor() {
        return sensorImplRepository.findAllDto();
    }
}
