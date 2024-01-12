package fr.lespimpons.simulator.component;

import fr.lespimpons.simulator.object.Fire;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class FireSingleton {
    @Getter
    private static final FireSingleton instance = new FireSingleton();

    @Getter
    private final List<Fire> fireList = new ArrayList<>();

    private FireSingleton() {
        // Constructeur privé pour empêcher l'instanciation directe
    }

    public void addFire(Fire fire) {
        fireList.add(fire);
    }

    public void removeFire(Fire fire) {
        fireList.remove(fire);
    }
}
