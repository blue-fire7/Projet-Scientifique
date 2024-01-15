package fr.lespimpons.simulator.component;

import fr.lespimpons.application.api.internal.service.WebsocketService;
import fr.lespimpons.application.logic.internal.repository.FireTruckRepository;
import fr.lespimpons.simulator.controller.SensorController;
import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.object.Fire;
import fr.lespimpons.simulator.services.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class Scheduler {

    private SensorController sensorController;
    private WebSocketService webSocketService;
    private InterventionSingleton interventionSingleton;

    public Scheduler(SensorController controller, WebSocketService webSocketService, InterventionSingleton interventionSingleton){
        this.sensorController = controller;
        this.webSocketService = webSocketService;
        this.interventionSingleton = interventionSingleton;
    }

    @Scheduled(fixedDelay = 10000)
    public void scheduler() throws InterruptedException {
        System.out.println("wait");
        //Récupération de la liste des feux
        List<Fire> fireList = FireSingleton.getInstance().getFireList();

        //Augmentation de la puissance
        for (Fire fire : fireList){
            fire.setDiameter(fire.getDiameter() + 10 * fire.getPower());
            System.out.println("Fire : id : " +fire.getId() + " Lat : "+fire.getLatitude() + " Long : " + fire.getLongitude()+" Diamètre : "+fire.getDiameter());
        }

        //Diminution de la puissance
        sensorController.checkFires(fireList);

        for(Fire fire: fireList){
            if(fire.getDiameter() <=0){
                interventionSingleton.getInterventionList().removeIf(intervention -> intervention.getFire().getId() == fire.getId());
                fireList.remove(fire);
            }
        }

        for (Fire fire : fireList){
            System.out.println("Fire : id : " +fire.getId() + " Diamètre : "+fire.getDiameter());
        }

        //Envoie des capteurs touchés
        sensorController.sensorsOnFire(fireList);

        //Renvoie des feux à la simulation
        webSocketService.updateFireList(fireList);

    }
}
