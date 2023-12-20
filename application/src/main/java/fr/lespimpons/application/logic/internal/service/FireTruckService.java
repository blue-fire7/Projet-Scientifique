package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.internal.entity.FireTruck;
import fr.lespimpons.application.logic.internal.repository.FireTruckRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FireTruckService {

    private final FireTruckRepository fireTruckRepository;


    public List<FireTruckDto> getAllFireTruckDto() {
        return fireTruckRepository.findAll().stream().map(this::mapFireTruckToFireTruckDto)
                .collect(Collectors.toList());
    }

    private FireTruckDto mapFireTruckToFireTruckDto(FireTruck fireTruck) {
        return new FireTruckDto(fireTruck.getId(), fireTruck.getLongitude().doubleValue(), fireTruck.getLatitude()
                .doubleValue(), fireTruck.getFireTruckType().getType());
    }
}
