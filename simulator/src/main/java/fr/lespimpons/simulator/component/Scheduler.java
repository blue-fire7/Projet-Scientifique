package fr.lespimpons.simulator.component;

import fr.lespimpons.application.api.internal.service.WebsocketService;
import fr.lespimpons.simulator.controller.SensorController;
import fr.lespimpons.simulator.object.Fire;
import fr.lespimpons.simulator.services.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class Scheduler {

    private SensorController sensorController;
    private WebSocketService webSocketService;

    public Scheduler(SensorController controller, WebSocketService webSocketService){
        this.sensorController = controller;
        this.webSocketService = webSocketService;
    }

    @Scheduled(fixedDelay = 5000)
    public void scheduler() throws InterruptedException {

        //Récupération de la liste des feux
        List<Fire> fireList = FireSingleton.getInstance().getFireList();

        //Augmentation de la puissance
        for (Fire fire : fireList){
            fire.setDiameter(fire.getDiameter() + 100);
            System.out.println("Fire : " +fire.getLatitude() + fire.getLongitude()+" Diamètre : "+fire.getDiameter());
        }

        System.out.println("wait");

        //Envoie des capteurs touchés
        sensorController.sensorsOnFire(fireList);

        //Renvoie des feux à la simulation
        webSocketService.updateFireList(fireList);

    }
}
