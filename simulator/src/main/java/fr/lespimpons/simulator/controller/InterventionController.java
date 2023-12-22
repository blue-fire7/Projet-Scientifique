package fr.lespimpons.simulator.controller;

import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.services.InterventionService;
import fr.lespimpons.simulator.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class InterventionController {
    @Autowired
    private InterventionService service;

    @GetMapping("/intervention")
    public List<Intervention> findAll() {
        return service.findAll();
    }
}
