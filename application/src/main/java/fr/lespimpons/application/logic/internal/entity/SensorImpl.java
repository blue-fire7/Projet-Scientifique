package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Point;
import lombok.Data;

@Data
public class SensorImpl implements Sensor {

    Point position;
    Integer intensity;

}
