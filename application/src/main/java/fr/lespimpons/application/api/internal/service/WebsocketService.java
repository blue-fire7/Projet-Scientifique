package fr.lespimpons.application.api.internal.service;

import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void updateSensor(SensorDto sensorDto) {
        messagingTemplate.convertAndSend("/topic/update/sensor", sensorDto);
    }

    public void updateFireTruck(FireTruckDto fireTruckDto) {
        messagingTemplate.convertAndSend("/topic/update/fireTruck", fireTruckDto);
    }

}
