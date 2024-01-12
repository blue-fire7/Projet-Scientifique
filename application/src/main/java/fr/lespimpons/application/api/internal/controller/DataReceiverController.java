package fr.lespimpons.application.api.internal.controller;

import fr.lespimpons.application.api.ApiManagement;
import fr.lespimpons.application.api.internal.controller.dto.FireSensorDto;
import fr.lespimpons.application.api.internal.controller.dto.TruckSensorDtoFromApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DataReceiverController {

    private final ApiManagement apiManagement;

    @PostMapping("fire-sensor")
    public void receiveFireSensor(@RequestBody FireSensorDto fireSensorDto) {
        apiManagement.receiveSensorEvent(fireSensorDto);
    }

    @PostMapping("truck-location")
    public void receiveTruckLocation(@RequestBody TruckSensorDtoFromApi truckSensorDtoFromApi) {
        apiManagement.receiveTruckLocationEvent(truckSensorDtoFromApi);

    }

}
