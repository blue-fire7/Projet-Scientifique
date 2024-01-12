package fr.lespimpons.simulator.component;


import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.repository.FireTruckRepository;
import fr.lespimpons.simulator.repository.InterventionRepository;
import fr.lespimpons.simulator.services.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TruckScheduler {
    private WebSocketService webSocketService;
    private List<FireTruck> truckList;
    private FireTruckRepository fireTruckRepository;
    private InterventionRepository interventionRepository;
    private TruckSingleton truckSingleton;
    public TruckScheduler(WebSocketService webSocketService, FireTruckRepository fireTruckRepository, InterventionRepository interventionRepository, TruckSingleton truckSingleton){
        this.truckList = new ArrayList<>();
        this.webSocketService = webSocketService;
        this.fireTruckRepository = fireTruckRepository;
        this.interventionRepository = interventionRepository;
        this.truckSingleton = truckSingleton;
    }

    @Scheduled(fixedDelay = 5000)
    public void doTick(){
        List<FireTruck> trucksInIntervention = fireTruckRepository.findTruckInIntervention();
        log.info(trucksInIntervention.toString());
        this.truckSingleton.truckList = trucksInIntervention;


    }


}
