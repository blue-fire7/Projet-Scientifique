package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.internal.mapper.FireTruckMapper;
import fr.lespimpons.application.logic.internal.repository.FireTruckRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FireTruckService {

    private static FireTruckService instance;
    private final FireTruckRepositoryImpl fireTruckRepository;

    private FireTruckService() {
        this.fireTruckRepository = FireTruckRepositoryImpl.getInstance();
    }

    public static FireTruckService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (FireTruckService.class) {
            instance = new FireTruckService();
            return instance;
        }
    }

    public List<FireTruckDto> getAllFireTruckDtoOnIntervention() {
        return fireTruckRepository.getFireTruckOnIntervention().stream().map(FireTruckMapper::toDto)
                .collect(Collectors.toList());
    }


    public Integer getAvailableFireTruck(Long id) {
        return fireTruckRepository.getAvailableFireTruck(id);
    }

    public Integer getFiretruckOfStation(Long id) {
        return fireTruckRepository.getFiretruckOfStation(id);
    }
}
