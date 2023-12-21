package fr.lespimpons.simulator.controller;

import fr.lespimpons.simulator.entity.Fire;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.repository.FireRepository;
import fr.lespimpons.simulator.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class FireController {
    @Autowired
    private FireRepository repository;

    @GetMapping("/fire")
    public List<Fire> findAll() {
        return repository.findAll();
    }
}
