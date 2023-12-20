package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Point;

public interface Sensor {

    void setPosition(Point position);
    Point getPosition();

/*    void setIntensity(Integer intensity);

    Integer getIntensity();*/
}
