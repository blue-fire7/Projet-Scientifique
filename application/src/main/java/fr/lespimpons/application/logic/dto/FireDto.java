package fr.lespimpons.application.logic.dto;

import fr.lespimpons.application.pojo.geometry.Circle;

import java.time.LocalDate;


public record FireDto(Long id, Circle circle, LocalDate creationDate, LocalDate extinctionDate, double power) {

}
