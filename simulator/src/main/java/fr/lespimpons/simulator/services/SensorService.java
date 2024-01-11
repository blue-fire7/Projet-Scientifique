package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.repository.FireRepository;
import fr.lespimpons.simulator.repository.InterventionRepository;
import fr.lespimpons.simulator.repository.SensorEventRepository;
import fr.lespimpons.simulator.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorService {

    private final SensorRepository repository;
    private final InterventionRepository interventionRepository;
    private final SensorEventRepository sensorEventRepository;

    public List<Sensor> findAll() {
        return repository.findAll();
    }

    public List<Intervention> findInterventionByFireId(Long fireId){
        return interventionRepository.findInterventionsByFire_Id(fireId);
    }



}
