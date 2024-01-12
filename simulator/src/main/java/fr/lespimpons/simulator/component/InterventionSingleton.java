package fr.lespimpons.simulator.component;

import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@AllArgsConstructor
public class InterventionSingleton {

    @Getter
    @Setter
    private List<Intervention> interventionList;

    public InterventionSingleton(){
        interventionList = new ArrayList<>();
    }

    public List<FireTruck> getTrucks () {
        List<FireTruck> trucks = new ArrayList<>();
        this.interventionList.stream().forEach(intervention -> {
            trucks.add(intervention.getFireTruck());
        });
        return trucks;
    }
}
