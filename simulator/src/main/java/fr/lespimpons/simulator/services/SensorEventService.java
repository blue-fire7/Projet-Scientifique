package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.repository.InterventionRepository;
import fr.lespimpons.simulator.repository.SensorEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorEventService {
    private final SensorEventRepository repository;

    public List<SensorEvent> findAll() {
        return repository.findAll();
    }
}
