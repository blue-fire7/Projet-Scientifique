package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.logic.internal.entity.FireImpl;
import fr.lespimpons.application.logic.internal.entity.SensorEvent;
import fr.lespimpons.application.logic.internal.entity.SensorEventId;
import fr.lespimpons.application.logic.internal.entity.SensorImpl;
import fr.lespimpons.application.logic.internal.repository.FireImplRepository;
import fr.lespimpons.application.logic.internal.repository.SensorEventRepository;
import fr.lespimpons.application.logic.internal.repository.SensorImplRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FireService {

    private final static double RADIUS = 500d;

    private final SensorEventRepository sensorEventRepository;

    private final SensorImplRepository sensorImplRepository;

    private final FireImplRepository fireImplRepository;

    private final EmergencyService emergencyService;

    @PostConstruct
    @Transactional
    public void test() {
        FireSensorDto fireSensorDto = new FireSensorDto(1L, 1, LocalDateTime.now());
        updateFire(fireSensorDto);
    }

    @Transactional
    public void updateFire(FireSensorDto sensorDto) {
        // on recup toutes les sensors dans le rayon X
        // on regarde si il y a un feu
        // si oui on update le feu
        // si non on crée un feu

        SensorImpl sensor = sensorImplRepository.findById(sensorDto.id()).orElseThrow();


        //SI 0 ALORS ON REGARDE SI LE FEU EST FINI
        if (sensorDto.intensity() == 0) {
            checkIfFire(sensor);
            return;
        }

        //on recupère tous les évènements de feux dans le rayon X avec un feu actif
        List<SensorEvent> allSensor = sensorEventRepository.findAllInAreaWithLevelN(sensor.getLongitude()
                .doubleValue(), sensor.getLatitude().doubleValue(), RADIUS);


        if (allSensor.isEmpty() || true) { //TODO
            log.info("No fire in the area");
            SensorEvent fireEvent = createFire(sensor, sensorDto.intensity());

            log.info("Fire created {}", fireEvent);
            log.info("Emergency service called");
            emergencyService.sendEmergency(sensor.getPosition(), fireEvent.getFireImpl());

        } else {
            log.info("Fire in the area");
        }


    }

    private void checkIfFire(SensorImpl sensor) {
        //on recupère le dernier évènement de feu
        FireImpl fire = fireImplRepository.findLastFireBySensorId(sensor.getId());
        if (fire == null || fire.getEndedAt() != null) {
            log.info("No more fire in the area");
            return;
        } else {
            log.info("Fire in the area");

            //check si encore un capteur du feu est actif
            List<SensorImpl> allSensor = sensorImplRepository.findAllSensorByFireId(fire.getId());

            boolean feuxEnCours = allSensor.stream()
                    .anyMatch(sensor1 -> sensorImplRepository.lastLevelBySensorId(sensor1.getId()) > 0);

            if (feuxEnCours) {
                log.info("Fire still in the area");
                return;
            } else {
                log.info("Fire ended");
                fire.setEndedAt(LocalDateTime.now());
                fireImplRepository.saveAndFlush(fire);
            }

        }
        //on met à jour la date de fin du feu
        fire.setEndedAt(LocalDateTime.now());
        //on sauvegarde
        fireImplRepository.saveAndFlush(fire);
    }

    @Transactional
    public SensorEvent createFire(SensorImpl sensor, int intensity) {
        log.info("Create fire");

        FireImpl fire = FireImpl.builder().startedAt(LocalDateTime.now()).build();
        fire = fireImplRepository.saveAndFlush(fire);

        SensorEvent sensorEvent = SensorEvent.builder()
                .id(SensorEventId.builder().fireId(fire.getId()).sensorId(sensor.getId()).updateAt(LocalDateTime.now())
                        .build()).level(intensity).fireImpl(fire).sensorImpl(sensor).build();

        return sensorEventRepository.saveAndFlush(sensorEvent);

    }


}
