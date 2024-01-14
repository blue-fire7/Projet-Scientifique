package fr.lespimpons.application.logic;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDtoFromApi;
import fr.lespimpons.application.event.EventService;
import fr.lespimpons.application.event.Listener;
import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.dto.StationDto;
import fr.lespimpons.application.logic.internal.mapper.StationMapper;
import fr.lespimpons.application.logic.internal.service.FireService;
import fr.lespimpons.application.logic.internal.service.FireTruckService;
import fr.lespimpons.application.logic.internal.service.SensorService;
import fr.lespimpons.application.logic.internal.service.StationService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LogicManagement {


    private static LogicManagement instance;
    private final EventService eventService;
    private final SensorService sensorService;
    private final FireService fireService;
    private final StationService stationService;
    private final FireTruckService fireTruckService;

    private LogicManagement() {
        this.eventService = EventService.getInstance();
        this.sensorService = SensorService.getInstance();
        this.fireService = FireService.getInstance();
        this.stationService = StationService.getInstance();
        this.fireTruckService = FireTruckService.getInstance();
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

    public List<SensorDto> getAllSensors() {
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

    @Listener(TruckSensorDtoFromApi.class)
    public synchronized void receiveTruckLocationEvent(TruckSensorDtoFromApi truckSensorDtoFromApi) {
        log.info("Received truck location event: {}", truckSensorDtoFromApi);
        this.fireService.updateTruckLocation(truckSensorDtoFromApi);
    }


    public List<StationDto> getAllFireStations() {
        return stationService.getAllFireStations().stream().map(StationMapper::toDto).collect(Collectors.toList());
    }

    public List<FireTruckDto> getAllFireTrucks() {
        return fireTruckService.getAllFireTruckDto();
    }

}
