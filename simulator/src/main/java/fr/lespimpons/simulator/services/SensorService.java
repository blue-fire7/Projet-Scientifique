package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorService {

    private final SensorRepository repository;
    public List<Sensor> findAll() {
        return repository.findAll();
    }
}
