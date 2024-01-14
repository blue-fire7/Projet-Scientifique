package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.internal.entity.FireTruck;
import fr.lespimpons.application.logic.internal.repository.FireTruckRepositoryImpl;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
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

    public List<FireTruckDto> getAllFireTruckDto() {
        return fireTruckRepository.findAll().stream().map(this::mapFireTruckToFireTruckDto)
                .collect(Collectors.toList());
    }

    private FireTruckDto mapFireTruckToFireTruckDto(FireTruck fireTruck) {


        BigDecimal longitude = fireTruck.getLongitude();
        if (longitude == null) {
            longitude = BigDecimal.ZERO;
        }

        BigDecimal latitude = fireTruck.getLatitude();
        if (latitude == null) {
            latitude = BigDecimal.ZERO;
        }
        return new FireTruckDto(fireTruck.getId(), longitude.doubleValue(), latitude.doubleValue(), fireTruck.getFireTruckType()
                .getType());
    }

    public Integer getAvailableFireTruck(Long id) {
    return fireTruckRepository.getAvailableFireTruck(id);
    }
    public Integer getFiretruckOfStation(Long id) {
        return fireTruckRepository.getFiretruckOfStation(id);
    }
}
