package fr.lespimpons.application.logic;

import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.service.SensorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LogicManagement {

    private final ApplicationEventPublisher events;
    private final SensorService sensorService;

  /*  @Transactional
    public void fireEvent(FireDto fire) {
        events.publishEvent(new FireDto(fire.id(), fire.));
    }*/
    @Transactional
    public void sendSensorEvent(SensorDto sensorDto) {
        events.publishEvent(sensorDto);
    }

    @Async
    @TransactionalEventListener
    public void receiveFireEvent(TruckSensorDto truckSensorDto) {
        log.info("Received fire event: {}", truckSensorDto);
    }

    public List<SensorDto> getAllSensor() {
        return sensorService.getAllSensor();
    }


}
