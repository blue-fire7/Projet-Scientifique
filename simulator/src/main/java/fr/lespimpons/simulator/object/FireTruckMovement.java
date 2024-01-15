package fr.lespimpons.simulator.object;

import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FireTruckMovement {
    private List<Position> positionList;
    private int progression;
    private FireTruck fireTruck;

    public FireTruckMovement(FireTruck fireTruck, List<Position> positionList) {
        this.fireTruck = fireTruck;
        this.positionList = positionList;
        this.progression = 0;
    }

    public Position getDiffPosition() {
        return new Position(this.positionList.get(1).getLatitude() - this.positionList.get(0).getLatitude(),
                this.positionList.get(1).getLongitude() - this.positionList.get(0).getLongitude());
    }
}
