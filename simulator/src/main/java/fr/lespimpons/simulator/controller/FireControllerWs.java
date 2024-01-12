package fr.lespimpons.simulator.controller;


import fr.lespimpons.simulator.component.FireSingleton;
import fr.lespimpons.simulator.object.Fire;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class FireControllerWs {

    @MessageMapping("addFires")
    public void addFires(List<Fire> fires){
        System.out.println("ouai");
        System.out.println(fires);
        FireSingleton.getInstance().getFireList().addAll(fires);
    }

    @MessageMapping("stopSimulation")
    public void stopSimulation(){
        System.out.println("Stop simu");
        FireSingleton.getInstance().getFireList().clear();
    }
}
