package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDtoFromApi;
import fr.lespimpons.application.event.EventService;
import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.internal.entity.*;
import fr.lespimpons.application.logic.internal.mapper.SensorMapper;
import fr.lespimpons.application.logic.internal.repository.FireImplRepositoryImpl;
import fr.lespimpons.application.logic.internal.repository.FireTruckRepositoryImpl;
import fr.lespimpons.application.logic.internal.repository.SensorEventRepositoryImpl;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
public class FireService {

    private final static double RADIUS = 500d;
    private static FireService instance;
    private final SensorEventRepositoryImpl sensorEventRepository;
    private final SensorImplRepositoryImpl sensorImplRepository;
    private final FireImplRepositoryImpl fireImplRepository;
    private final EmergencyService emergencyService;
    private final EventService eventService;
    private final FireTruckRepositoryImpl fireTruckRepository;


    private FireService() {
        this.eventService = EventService.getInstance();
        this.sensorEventRepository = SensorEventRepositoryImpl.getInstance();
        this.sensorImplRepository = SensorImplRepositoryImpl.getInstance();
        this.fireImplRepository = FireImplRepositoryImpl.getInstance();
        this.emergencyService = EmergencyService.getInstance();
        this.fireTruckRepository = FireTruckRepositoryImpl.getInstance();
    }

    public static FireService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (FireService.class) {
            if (instance == null) {
                instance = new FireService();
            }
        }
        return instance;
    }

    @Transactional
    public void updateFire(FireSensorDto sensorDto) {
        // on recup toutes les sensors dans le rayon X
        // on regarde si il y a un feu
        // si oui on update le feu
        // si non on crée un feu

        SensorImpl sensor = sensorImplRepository.findById(sensorDto.id()); // sensor updater
        if (sensor == null) {
            log.info("Sensor not found");
            throw new RuntimeException("Sensor not found");
        }

        this.eventService.publishEvent(SensorMapper.toDto(sensor, sensorDto.intensity())); // on publish pour le ws


        //SI 0 ALORS ON REGARDE SI LE FEU EST FINI
        if (sensorDto.intensity() == 0) {
            checkIfFireAndTryToFinishHim(sensor);
            saveZeroSensorEvent(sensor);
            return;
        }

        //on recupère tous les évènements de feux dans le rayon X avec un feu actif
        List<SensorEventImpl> allSensor = sensorEventRepository.findAllInAreaWithLevel(sensor.getLongitude()
                .doubleValue(), sensor.getLatitude().doubleValue(), RADIUS);



        if (allSensor.isEmpty()) {
            log.info("No fire in the area");
            SensorEventImpl fireEvent = createFireAndSave(sensor, sensorDto.intensity());
            log.info("Fire created {}", fireEvent);
            emergencyService.sendEmergency(sensor.getPosition(), fireEvent.getFireImpl());
            log.info("Emergency sent");
        } else {
            log.info("Fire in the area");

            //on recupère le feu du premier sensor, tri de l'ordre des capteurs dans le repository
            FireImpl fire = allSensor.get(0).getFireImpl();

            SensorEventImpl sensorEventImpl = SensorEventImpl.builder()
                    .id(SensorEventId.builder().fireId(fire.getId()).sensorId(sensor.getId())
                            .updateAt(LocalDateTime.now()).build()).level(sensorDto.intensity()).sensorImpl(sensor)
                    .fireImpl(fire).build();
            sensorEventRepository.saveAndFlush(sensorEventImpl);

            //TODO: voir si on update le feu ou si on en crée un nouveau
        }


    }

    private void saveZeroSensorEvent(SensorImpl sensor) {
        SensorEventImpl lastSensorEventBySensorId = sensorEventRepository.findLastSensorEventBySensorId(sensor.getId());

        if (lastSensorEventBySensorId != null && lastSensorEventBySensorId.getLevel() != 0) {
            log.info("Fire intensity updated to 0");

            SensorEventImpl sensorEventImpl = SensorEventImpl.builder()
                    .id(SensorEventId.builder().fireId(lastSensorEventBySensorId.getFireImpl().getId())
                            .sensorId(sensor.getId()).updateAt(LocalDateTime.now()).build()).level(0).sensorImpl(sensor)
                    .fireImpl(lastSensorEventBySensorId.getFireImpl()).build();

            sensorEventRepository.saveAndFlush(sensorEventImpl);
        }
    }

    /**
     * Check si le dernier feu lié au sensor est terminé
     * Si oui on update la date de fin
     *
     * @param sensor
     */
    private boolean checkIfFireAndTryToFinishHim(SensorImpl sensor) {
        //on recupère le dernier évènement de feu
        FireImpl fire = fireImplRepository.findLastFireBySensorId(sensor.getId());
        if (fire == null || fire.getEndedAt() != null) {
            log.info("No more fire in the area");
            return false;
        } else {
            //check si encore un capteur du feu est actif
            List<SensorImpl> allSensor = sensorImplRepository.findAllSensorByFireId(fire.getId());
            //on remove le sensor actuel
            allSensor.remove(sensor);

            boolean feuxEnCours = allSensor.stream()
                    .anyMatch(sensor1 -> sensorImplRepository.lastLevelBySensorId(sensor1.getId()) > 0);

            if (feuxEnCours) {
                log.info("Fire still in the area");
                return true;
            } else {
                log.info("Fire ended");
                fire.setEndedAt(LocalDateTime.now());
                fireImplRepository.saveAndFlush(fire);
                return false;
            }

        }
    }

    @Transactional
    public SensorEventImpl createFireAndSave(SensorImpl sensor, int intensity) {
        log.info("Create fire");

        FireImpl fire = FireImpl.builder().startedAt(LocalDateTime.now()).build();
        fire = fireImplRepository.saveAndFlush(fire);

        SensorEventImpl sensorEventImpl = SensorEventImpl.builder()
                .id(SensorEventId.builder().fireId(fire.getId()).sensorId(sensor.getId()).updateAt(LocalDateTime.now())
                        .build()).level(intensity).sensorImpl(sensor).fireImpl(fire).build();

        return sensorEventRepository.saveAndFlush(sensorEventImpl);
    }


    public FireTruck updateTruckLocation(TruckSensorDtoFromApi truckSensorDtoFromApi) {
        FireTruck fireTruck = fireTruckRepository.findById(truckSensorDtoFromApi.id());

        if (fireTruck == null) {
            throw new RuntimeException("Fire truck not found");
        }

        fireTruck.setLatitude(truckSensorDtoFromApi.latitude());
        fireTruck.setLongitude(truckSensorDtoFromApi.longitude());


        fireTruck = fireTruckRepository.saveAndFlush(fireTruck);

        FireTruckDto fireTruckDto = new FireTruckDto(fireTruck.getId(), fireTruck.getLongitude()
                .doubleValue(), fireTruck.getLatitude().doubleValue(), fireTruck.getFireTruckType().getType());
        eventService.publishEvent(fireTruckDto);
        return fireTruck;
    }
}
