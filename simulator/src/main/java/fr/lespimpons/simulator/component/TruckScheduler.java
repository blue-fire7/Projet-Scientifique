package fr.lespimpons.simulator.component;


import fr.lespimpons.simulator.controller.SensorController;
import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.object.Fire;
import fr.lespimpons.simulator.object.FireTruckMovement;
import fr.lespimpons.simulator.object.Position;
import fr.lespimpons.simulator.object.dto.FireTruckDto;
import fr.lespimpons.simulator.repository.FireTruckRepository;
import fr.lespimpons.simulator.repository.InterventionRepository;
import fr.lespimpons.simulator.repository.SensorEventRepository;
import fr.lespimpons.simulator.services.FireTruckService;
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
    private final InterventionRepository interventionRepository;
    private final InterventionSingleton interventionSingleton;
    private final SensorEventRepository sensorEventRepository;

    private final FireTruckService fireTruckService;

    private final List<FireTruckMovement> movementList;

    public TruckScheduler(WebSocketService webSocketService, InterventionRepository interventionRepository, InterventionSingleton interventionSingleton, SensorEventRepository sensorEventRepository, FireTruckService fireTruckService) {
        this.webSocketService = webSocketService;
        this.interventionRepository = interventionRepository;
        this.interventionSingleton = interventionSingleton;
        this.movementList = new ArrayList<FireTruckMovement>();
        this.sensorEventRepository = sensorEventRepository;
        this.fireTruckService = fireTruckService;
    }

    @Scheduled(fixedDelay = 5000)
    public void doTick() {
        List<Intervention> activeInterventions = interventionRepository.findActiveInterventions();
        this.interventionSingleton.getInterventionList().addAll(activeInterventions.stream().filter(intervention -> this.interventionSingleton.getInterventionList().stream().noneMatch(intervention1 -> Objects.equals(intervention.getId(), intervention1.getId()))).toList());
        moveTrucks();
    }

    @Transactional
    public void moveTrucks() {
        this.interventionSingleton.getInterventionList().forEach(intervention -> {
            FireTruck fireTruck = intervention.getFireTruck();
            List<Fire> fireList = FireSingleton.getInstance().getFireList();
            Optional<FireTruckMovement> move = movementList.stream().filter(fireTruckMovement -> fireTruckMovement.getFireTruck().getId().equals(fireTruck.getId())).findFirst();
            if (move.isPresent()) {
                //Avancer d'un dizieme du chemin
                FireTruckMovement fireTruckMovement = move.get();
                if (fireTruckMovement.getProgression() < 100) {
                    //On vérifie si le camion est dans une zone de feu
                    if(fireTruckMovement.getFireDestination() == null){
                        for(Fire fire: fireList){
                            if(SensorController.isFireTruckInFire(fire, fireTruckMovement.getFireTruck()) && Objects.equals(fire.getId(), intervention.getFire().getId())){
                                fireTruckMovement.setFireDestination(fire);
                                Position destination = fireTruckMovement.getPositionList().get(1);
                                destination.setLatitude(fire.getLatitude());
                                destination.setLongitude(fire.getLongitude());
                                break;
                            }
                        }
                    }

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

        List<FireTruckDto> fireTruckDtos = new ArrayList<>();
        this.interventionSingleton.getTrucks().forEach(fireTruck -> {
            fireTruckDtos.add(new FireTruckDto(fireTruck));
        });

        webSocketService.sendTruckList(this.interventionSingleton.getTrucks());
        fireTruckService.sendFireTruckData(SensorController.convertObjectToJson(fireTruckDtos));
    }

}
