package fr.lespimpons.simulator.object.dto;

import fr.lespimpons.simulator.entity.FireTruck;

import java.math.BigDecimal;

public class FireTruckDto {
    public Long id;
    public Double latitude;
    public Double longitude;

    public FireTruckDto(FireTruck fireTruck)
    {
        this.id = fireTruck.getId();
        this.latitude = fireTruck.getLatitude();
        this.longitude = fireTruck.getLongitude();
    }
}
