
package fr.lespimpons.application.api.internal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FireController {

    public record FireRequest(double longitude, double latitude) {

    }

    @PostMapping("/add-fire")
    public String createFire(@RequestBody FireRequest fireRequest) {

        log.info(String.valueOf(fireRequest.latitude()));

        return "hello";
    }


}

