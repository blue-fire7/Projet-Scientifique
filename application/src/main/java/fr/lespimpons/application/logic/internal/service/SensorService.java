package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.mapper.SensorMapper;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorService {


    private final SensorImplRepository sensorImplRepository;

    public List<SensorDto> getAllSensor() {
        return sensorImplRepository.findAllDto();
    }



}
