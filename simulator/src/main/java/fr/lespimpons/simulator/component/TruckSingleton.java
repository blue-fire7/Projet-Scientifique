package fr.lespimpons.simulator.component;

import fr.lespimpons.simulator.entity.FireTruck;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class TruckSingleton {
    public List<FireTruck> truckList;


}
