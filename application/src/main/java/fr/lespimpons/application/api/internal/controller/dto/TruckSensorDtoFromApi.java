package fr.lespimpons.application.api.internal.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TruckSensorDtoFromApi(@NotNull Long id, @NotNull BigDecimal latitude, @NotNull BigDecimal longitude) {

}
