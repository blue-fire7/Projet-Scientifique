package fr.lespimpons.application.logic.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StationDto {
    private Long id;
    private double longitude;
    private double latitude;
    private Integer nbFireTruck;
    private Integer AvailableFireTruck;

    public StationDto(Long id, BigDecimal longitude, BigDecimal latitude) {
        this.id = id;
        this.longitude = longitude.doubleValue();
        this.latitude = latitude.doubleValue();
    }
}
