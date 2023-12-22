package fr.lespimpons.simulator.controller;

import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.repository.SensorEventRepository;
import fr.lespimpons.simulator.services.SensorEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class SensorEventController {
    @Autowired
    private SensorEventService service;

    @GetMapping("/sensor_event")
    public List<SensorEvent> findAll() {
        return service.findAll();
    }
}
