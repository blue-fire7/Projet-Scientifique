package fr.lespimpons.application.api;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.api.internal.service.WebsocketService;
import fr.lespimpons.application.event.EventService;
import fr.lespimpons.application.logic.dto.FireDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApiManagement {


    private final WebsocketService websocketService;

    private final EventService event = EventService.getInstance();


    public ApiManagement(WebsocketService websocketService) {
  /*      EventService.getInstance().addListener(FireDto.class, event -> new FireListener().onEvent(event));
        EventService.getInstance().addListener(SensorDto.class, event -> new SensorListener().onEvent(event));
*/
        this.websocketService = websocketService;
    }



/*    private class FireListener implements EventListener {

        @Override
        public void onEvent(Object event) {
            websocketService.updateFire((FireDto) event);
            log.info("Received fire event: {}", event);

        }
    }*/

/*    private class SensorListener implements EventListener {

        @Override
        public void onEvent(Object event) {
            websocketService.updateSensor((SensorDto) event);
            log.info("Received fire event: {}", event);
        }
    }*/


    public void receiveFireEvent(FireDto fire) {
        websocketService.updateFire(fire);
        log.info("Received fire event: {}", fire);
    }


    public void receiveSensorEvent(FireSensorDto sensorDto) {
        log.info("Received sensor event: {}", sensorDto);
        event.publishEvent(sensorDto);

    }


    public List<SensorDto> getAllSensor() {
        return (List<SensorDto>) EventService.getInstance().requestList(SensorDto.class);
    }

    public void onEvent(FireDto event) {
        websocketService.updateFire(event);
        log.info("Received fire event: {}", event);
    }






/*    @Async
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
    }*/

}
