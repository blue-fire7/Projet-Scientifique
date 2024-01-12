package fr.lespimpons.simulator.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {

    private Double Latitude;
    private Double Longitude;
    public Position(double latitude, double longitude) {
        this.Latitude = latitude;
        this.Longitude = longitude;
    }
}
