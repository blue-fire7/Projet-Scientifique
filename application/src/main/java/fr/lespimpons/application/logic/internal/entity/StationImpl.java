package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Point;
import lombok.Data;

@Data
public class StationImpl implements Station{

    Point stationPosition;
}
