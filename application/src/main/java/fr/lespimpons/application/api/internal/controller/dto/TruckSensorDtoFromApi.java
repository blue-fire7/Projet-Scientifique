package fr.lespimpons.application.api.internal.controller.dto;

import jakarta.validation.constraints.NotNull;

public record TruckSensorDtoFromApi(@NotNull Long id, @NotNull double latitude, @NotNull double longitude) {

}
