package fr.lespimpons.application.logic;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.event.EventService;
import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.service.FireService;
import fr.lespimpons.application.logic.internal.service.SensorService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/*@RequiredArgsConstructor
@Service*/
@Slf4j
public class LogicManagement {


    private static LogicManagement instance;
    private final EventService eventService;
    private final SensorService sensorService;
    private final FireService fireService;

    private LogicManagement() {
        this.eventService = EventService.getInstance();
        this.sensorService = SensorService.getInstance();
        fireService = FireService.getInstance();
    }

    public static LogicManagement getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (LogicManagement.class) {
            if (instance == null) {
                instance = new LogicManagement();
            }
        }
        return instance;
    }

    public void init() {

    }

    private List<SensorDto> getAllSensor() {
        return sensorService.getAllSensor();
    }

    public void sendSensorEvent(SensorDto sensorDto) {
        eventService.publishEvent(sensorDto);
    }


    @Listener(FireSensorDto.class)
    public void receiveSensorEvent(FireSensorDto sensorDto) {
        log.info("Received sensor event: {}", sensorDto);
        this.fireService.updateFire(sensorDto);
    }


    public Object receiveSensor(SensorDto sensorDto) {
        log.info("Received sensor event: {}", sensorDto);
        return null;
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
