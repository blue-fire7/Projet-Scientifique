package fr.lespimpons.application.api.internal.controller.dto;


import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record FireSensorDto(
        @NotNull Long id, @NotNull Integer intensity, LocalTime timestamp
) {

}
