package fr.lespimpons.simulator.object;

import fr.lespimpons.simulator.entity.Sensor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Fire {
    private Long id;

    private double latitude;

    private double longitude;

    private int diameter;

    private int power;

    private List<Sensor> sensorList;
}
