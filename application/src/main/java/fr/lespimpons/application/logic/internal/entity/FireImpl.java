package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Circle;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FireImpl implements Fire {

    Long id;
    Circle circle;
    LocalDate creationDate;
    LocalDate extinctionDate;
    //Facteur de propagation
    public double power = 1;

    public void extinguishFire(FireTruck fireTruck){
        if (this.circle.contains(fireTruck.getCurrentPosition())) {
            this.circle.reduceByArea(fireTruck.getClearAreaInSquareMeters());
        }
    }
    public Fire propagateFire(){
        //TODO
        return this;
    }




}
