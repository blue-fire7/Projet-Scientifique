package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.internal.entity.*;
import fr.lespimpons.application.logic.internal.repository.*;
import fr.lespimpons.application.logic.internal.utils.GeometryUtils;
import fr.lespimpons.application.pojo.geometry.Point;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Getter
@Setter
public class EmergencyService {
    private static EmergencyService instance;
    private final InterventionRepositoryImpl interventionRepository;
    private final StationRepository stationRepository;
    private final FireImplRepository fireImplRepository;
    private final FireTruckRepository fireTruckRepository;
    private final TeamRepository teamRepository;
    private final Queue<FireImpl> fireQueue = new LinkedList<>();

    private EmergencyService() {
        this.interventionRepository = InterventionRepositoryImpl.getInstance();
        this.stationRepository = StationImplRepository.getInstance();
        this.fireImplRepository = FireImplRepositoryImpl.getInstance();
        this.fireTruckRepository = FireTruckRepositoryImpl.getInstance();
        this.teamRepository = TeamRepositoryImpl.getInstance();
    }

    public static EmergencyService getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (EmergencyService.class) {
            if (instance == null) {
                instance = new EmergencyService();
            }
        }
        return instance;
    }


    public void sendEmergency(Point position, FireImpl fire) {
        //on trouve la caserne la plus proche avec un camion et une équipe dispo

        // on trouve la caserne la plus proche avec un camion et une équipe dispo

        List<StationImpl> stations = stationRepository.findWithDispo();

        stations.sort((s1, s2) -> {
            double distance1 = GeometryUtils.calculateDistance(position, s1.getPosition());
            double distance2 = GeometryUtils.calculateDistance(position, s2.getPosition());
            return Double.compare(distance1, distance2);
        });

        if (stations.isEmpty()) {
            log.info("No station available");
            fireQueue.add(fire);
            return;
        }

        List<FireTruck> fireTrucks = fireTruckRepository.findFireTruckDispoByStationId(stations.get(0).getId());
        List<Team> teams = teamRepository.findTeamDispoByStationId(stations.get(0).getId());

        //
        Intervention intervention = Intervention
                .builder()
                .id(InterventionId.builder()
                        .build())
                .team(teams.get(0))
                .fire(fire)
                .fireTruck(fireTrucks.get(0))
                .build();
        log.info("Intervention created : {}", intervention);
        interventionRepository.saveAndFlush(intervention);
    }


}
