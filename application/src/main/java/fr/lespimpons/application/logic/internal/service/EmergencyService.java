package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.internal.entity.*;
import fr.lespimpons.application.logic.internal.repository.*;
import fr.lespimpons.application.logic.internal.utils.GeometryUtils;
import fr.lespimpons.application.pojo.geometry.Point;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
@Setter
public class EmergencyService {
    private final InterventionRepository interventionRepository;

    private final StationImplRepository stationImplRepository;

    private final FireImplRepository fireImplRepository;
    private final FireTruckRepository fireTruckRepository;
    private final TeamRepository teamRepository;

    private final Queue<FireImpl> fireQueue = new ArrayDeque<>();

    public void sendEmergency(Point position, FireImpl fire) {
        //on trouve la caserne la plus proche avec un camion et une équipe dispo

        // on trouve la caserne la plus proche avec un camion et une équipe dispo

        List<StationImpl> stations = stationImplRepository.findWithDispo();

        stations.sort((s1, s2) -> {
            double distance1 = GeometryUtils.calculateDistance(position, s1.getPosition());
            double distance2 = GeometryUtils.calculateDistance(position, s2.getPosition());
            return Double.compare(distance1, distance2);
        });

        if(stations.isEmpty()) {
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
     /*                   .fireId(fire.getId())
                        .fireTruckId(fireTrucks.get(0).getId())
                        .teamId(teams.get(0).getId())*/
                        .build())
               .team(teams.get(0))
               .fire(fire)
               .fireTruck(fireTrucks.get(0))
                .build();

        interventionRepository.save(intervention);
    }




}
