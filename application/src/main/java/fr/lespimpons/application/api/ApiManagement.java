package fr.lespimpons.application.api;

import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDto;
import fr.lespimpons.application.api.internal.service.WebsocketService;
import fr.lespimpons.application.logic.dto.FireDto;
import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.service.FireTruckService;
import fr.lespimpons.application.logic.internal.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiManagement {

    private final ApplicationEventPublisher events;

    private final WebsocketService websocketService;

    private final SensorService sensorService;

    private final FireTruckService fireTruckService;
    @Async
    @TransactionalEventListener
    public void receiveFireEvent(FireDto fire) {
        websocketService.updateFire(fire);
    log.info("Received fire event: {}", fire);
    }

    @Async
    @TransactionalEventListener
    public void receiveSensorEvent(SensorDto sensorDto) {
        websocketService.updateSensor(sensorDto);
        log.info("Received fire event: {}", sensorDto);
    }

    @Transactional
    public void fireEvent(TruckSensorDto truckSensorDto) {
        events.publishEvent(truckSensorDto);
    }

    public List<SensorDto> getAllSensor() {
        return sensorService.getAllSensor();
    }

    public List<FireTruckDto> getAllFireTrucks() {
        return fireTruckService.getAllFireTruckDto();
    }
}
