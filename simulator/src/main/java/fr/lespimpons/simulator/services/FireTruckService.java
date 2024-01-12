package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.repository.FireTruckRepository;
import fr.lespimpons.simulator.repository.InterventionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireTruckService {
    private final FireTruckRepository repository;

    public List<FireTruck> findAll() {
        return repository.findAll();
    }

    public List<FireTruck> findFireTrucksInIntervention(){
        return repository.findFireTruckInIntervention();
    }
}
