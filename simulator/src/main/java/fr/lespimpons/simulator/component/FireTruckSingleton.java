package fr.lespimpons.simulator.component;

import fr.lespimpons.simulator.entity.FireTruck;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;



public class FireTruckSingleton {
    @Getter
    private static final FireTruckSingleton instance = new FireTruckSingleton();

    @Getter
    private final List<FireTruck> fireTruckList = new ArrayList<>();

    private FireTruckSingleton() {
        // Constructeur privé pour empêcher l'instanciation directe
    }


}
