package fr.lespimpons.application.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SensorDto {

    private Long id;
    private double longitude;
    private double latitude;
    private Integer level;

    public SensorDto(Long id, BigDecimal longitude, BigDecimal latitude, Integer level) {
        this.id = id;
        this.longitude = longitude.doubleValue();
        this.latitude = latitude.doubleValue();
        this.level = level;
    }


}
