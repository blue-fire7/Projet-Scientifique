package fr.lespimpons.application.logic.dto;

import java.math.BigDecimal;

public record StationDto(Long id, double longitude, double latitude) {
    public StationDto(Long id, BigDecimal longitude, BigDecimal latitude) {
        this(id, longitude.doubleValue(), latitude.doubleValue());
    }
}
