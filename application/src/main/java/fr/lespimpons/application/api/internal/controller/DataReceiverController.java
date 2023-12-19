package fr.lespimpons.application.api.internal.controller;

import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DataReceiverController {

    @PostMapping("fire-sensor")
    public void receiveFireSensor(@RequestBody FireSensorDto fireSensorDto) {

    }

    @PostMapping("truck-location")
    public void receiveTruckLocation(@RequestBody TruckSensorDto truckSensorDto) {

    }

}
