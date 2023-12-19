package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Point;
import lombok.Data;

@Data
public class FireTruck implements Vehicle{

    Point currentPosition;
    Double clearAreaInSquareMeters;
    Station station;

    public void move(Point destination) {
        this.currentPosition = destination;
    }

    @Override
    public void returnToBase() {
        this.move(station.getStationPosition());
    }

}
