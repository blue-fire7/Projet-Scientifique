package fr.lespimpons.application.logic;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.event.EventListener;
import fr.lespimpons.application.event.EventService;
import fr.lespimpons.application.event.RequestListener;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.service.SensorService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*@RequiredArgsConstructor
@Service*/
@Slf4j
public class LogicManagement  {


    private static LogicManagement instance;

    public static LogicManagement getInstance() {
        if (instance == null) {
            instance = new LogicManagement();
        }
        return instance;
    }

    private EventService eventService;
    private SensorService sensorService;


    public void init() {

    }

    public LogicManagement() {
        this.eventService = EventService.getInstance();
        this.sensorService = SensorService.getInstance();
        eventService.addListener(FireSensorDto.class, event -> new SensorListener().onEvent(event));
       // eventService.addRequestListenerList(SensorDto.class, this::getAllSensor);

    }

    private List<SensorDto> getAllSensor() {
        return sensorService.getAllSensor();
    }

    public void sendSensorEvent(SensorDto sensorDto) {
        eventService.publishEvent(sensorDto);
    }


    public void receiveSensorEvent(FireSensorDto sensorDto) {
        log.info("Received sensor event: {}", sensorDto);
    }

    private class SensorListener implements EventListener<FireSensorDto> {

        @Override
        public void onEvent(Object event) {
            log.info("Received fire event: {}", event);
            receiveSensorEvent((FireSensorDto) event);
        }
    }


    /*    private final ApplicationEventPublisher events;
    private final SensorService sensorService;*/

  /*  @Transactional
    public void fireEvent(FireDto fire) {
        events.publishEvent(new FireDto(fire.id(), fire.));
    }*/
/*
    @Transactional
    public void sendSensorEvent(SensorDto sensorDto) {
        events.publishEvent(sensorDto);
    }


    public void receiveFireTruckEvent(TruckSensorDto truckSensorDto) {
        log.info("Received fire event: {}", truckSensorDto);
    }


    public void receiveSensorEvent(FireSensorDto fireSensorDto) {
        log.info("Received fire event: {}", fireSensorDto);
    }




    public List<SensorDto> getAllSensor() {
        return sensorService.getAllSensor();
    }
*/


}
