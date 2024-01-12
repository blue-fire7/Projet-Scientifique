package fr.lespimpons.application.api;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDtoFromApi;
import fr.lespimpons.application.api.internal.service.WebsocketService;
import fr.lespimpons.application.event.EventService;
import fr.lespimpons.application.event.Listener;
import fr.lespimpons.application.logic.LogicManagement;
import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.dto.StationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApiManagement {

    private static ApplicationContext context;
    private final WebsocketService websocketService;
    private final EventService event = EventService.getInstance();
    private final LogicManagement logicManagement = LogicManagement.getInstance();


    public ApiManagement(WebsocketService websocketService, ApplicationContext context) {
        this.websocketService = websocketService;
        ApiManagement.context = context;
    }

    public static ApiManagement getInstance() {
        //permet de récupérer le bean ApiManagement dans les autres classes (pas top...)
        return context.getBean(ApiManagement.class);
    }

    @Listener(SensorDto.class)
    public void receiveFireEvent(SensorDto fire) {
        websocketService.updateSensor(fire);
        log.info("Received fire event: {}", fire);
    }

    @Listener(FireTruckDto.class)
    public void receiveFireTruckEvent(FireTruckDto fireTruckDto) {
        websocketService.updateFireTruck(fireTruckDto);
        log.info("Received fire truck event: {}", fireTruckDto);
    }


    public void receiveSensorEvent(FireSensorDto sensorDto) {
        log.info("Received sensor event: {}", sensorDto);
        event.publishEvent(sensorDto);

    }


    public List<SensorDto> getAllSensors() {
        return logicManagement.getAllSensors();
    }

    public List<StationDto> getAllFireStations() {
        return logicManagement.getAllFireStations();
    }


    public void receiveTruckLocationEvent(TruckSensorDtoFromApi truckSensorDtoFromApi) {
        log.info("Received truck location event: {}", truckSensorDtoFromApi);
        event.publishEvent(truckSensorDtoFromApi);
    }

    public List<FireTruckDto> getAllFireTrucks() {
        return logicManagement.getAllFireTrucks();
    }
}
