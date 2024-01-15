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
    private Integer nbTeams;
    private Integer nbAvailableTeams;

    public StationDto(Long id, BigDecimal longitude, BigDecimal latitude) {
        this.id = id;
        this.longitude = longitude.doubleValue();
        this.latitude = latitude.doubleValue();
    }
}
