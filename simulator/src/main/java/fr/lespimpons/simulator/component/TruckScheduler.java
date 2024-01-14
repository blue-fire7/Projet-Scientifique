package fr.lespimpons.simulator.component;


import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.object.FireTruckMovement;
import fr.lespimpons.simulator.object.Position;
import fr.lespimpons.simulator.repository.FireTruckRepository;
import fr.lespimpons.simulator.repository.InterventionRepository;
import fr.lespimpons.simulator.repository.SensorEventRepository;
import fr.lespimpons.simulator.services.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Slf4j
public class TruckScheduler {
    private final WebSocketService webSocketService;
    private final FireTruckRepository fireTruckRepository;
    private final InterventionRepository interventionRepository;
    private final InterventionSingleton interventionSingleton;
    private final SensorEventRepository sensorEventRepository;

    private final List<FireTruckMovement> movementList;

    public TruckScheduler(WebSocketService webSocketService, FireTruckRepository fireTruckRepository, InterventionRepository interventionRepository, InterventionSingleton interventionSingleton, SensorEventRepository sensorEventRepository) {
        this.webSocketService = webSocketService;
        this.fireTruckRepository = fireTruckRepository;
        this.interventionRepository = interventionRepository;
        this.interventionSingleton = interventionSingleton;
        this.movementList = new ArrayList<FireTruckMovement>();
        this.sensorEventRepository = sensorEventRepository;
    }

    @Scheduled(fixedDelay = 10000)
    public void doTick() {
        List<Intervention> activeInterventions = interventionRepository.findActiveInterventions();
        this.interventionSingleton.getInterventionList().addAll(activeInterventions.stream().filter(intervention -> this.interventionSingleton.getInterventionList().stream().noneMatch(intervention1 -> Objects.equals(intervention.getId(), intervention1.getId()))).toList());
        moveTrucks();
    }

    @Transactional
    public void moveTrucks() {
        this.interventionSingleton.getInterventionList().forEach(intervention -> {
            FireTruck fireTruck = intervention.getFireTruck();
            Optional<FireTruckMovement> move = movementList.stream().filter(fireTruckMovement -> fireTruckMovement.getFireTruck().getId().equals(fireTruck.getId())).findFirst();
            if (move.isPresent()) {
                //Avancer d'un dizieme du chemin
                FireTruckMovement fireTruckMovement = move.get();
                if (fireTruckMovement.getProgression() < 100) {
                    fireTruckMovement.setProgression(fireTruckMovement.getProgression() + 10);

                    //distance entre les deux camions
                    Position distance = fireTruckMovement.getDiffPosition();

                    //On ajuste la position des camions
                    fireTruckMovement.getFireTruck().setLatitude(fireTruckMovement.getPositionList().get(0).getLatitude() + distance.getLatitude() * 0.01 * fireTruckMovement.getProgression());
                    fireTruckMovement.getFireTruck().setLongitude(fireTruckMovement.getPositionList().get(0).getLongitude()  + distance.getLongitude() * 0.01 * fireTruckMovement.getProgression());
                }
            } else {
                List<Position> positions = new ArrayList<>();
                positions.add(new Position(intervention.getFireTruck().getFireStation().getLatitude(), intervention.getFireTruck().getFireStation().getLongitude()));

                Sensor sensorDestination = sensorEventRepository.findMaxSensorEventByFireID(intervention.getFire().getId()).getSensor();
                positions.add(new Position(sensorDestination.getLatitude(), sensorDestination.getLongitude()));

                this.movementList.add(new FireTruckMovement(fireTruck, positions));
            }

        });

        webSocketService.sendTruckList(this.interventionSingleton.getTrucks());
    }

}
