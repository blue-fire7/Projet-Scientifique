package fr.lespimpons.application.api.internal.controller;

import fr.lespimpons.application.api.ApiManagement;
import fr.lespimpons.application.logic.dto.FireTruckDto;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.dto.StationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FrontController {

    private final ApiManagement apiManagement;


    @GetMapping(value = "/sensors", produces = "application/json")
    public ResponseEntity<List<SensorDto>> getAllSensors() {
        return ResponseEntity.ok(apiManagement.getAllSensors());
    }


    @GetMapping(value = "/fire-trucks", produces = "application/json")
    public ResponseEntity<List<FireTruckDto>> getAllFireTrucks() {
        return ResponseEntity.ok(apiManagement.getAllFireTrucksOnIntervention());
    }

    @GetMapping(value = "/fire-stations", produces = "application/json")
    public ResponseEntity<List<StationDto>> getAllFireStations() {
        return ResponseEntity.ok(apiManagement.getAllFireStations());
    }

    @GetMapping(value = "/fire-station/{id}", produces = "application/json")
    public ResponseEntity<StationDto> getAllFireStations(@PathVariable("id") Long id) {
        return ResponseEntity.ok(apiManagement.getFireStation(id));
    }

}
