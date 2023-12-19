package fr.lespimpons.simulator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireController {

    public record FireRequest(double longitude, double latitude) {

    }

    @PostMapping("/add-fire")
    public void createFire(@RequestBody FireRequest fireRequest) {

    }

}
