package fr.lespimpons.simulator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class SensorController {
    @Autowired
    private SensorService service;

    @GetMapping("/sensor")
    public List<Sensor> findAll() {
        return service.findAll();
    }

    public record Fire(double longitude, double latitude, int diameter) {
    }

    public record SensorOnFire(long id, long intensity) {
    }

    @PostMapping("/start-fire")
    public void startFire(@RequestBody List<Fire> listFire) {

    }

    @PostMapping("/sensors-on-fire")
    public void sensorsOnFire(@RequestBody List<Fire> listFire) {
        List<Sensor> sensorList = service.findAll();
        List<SensorOnFire> sensorOnFireList = new ArrayList<>();

        for (Fire fire : listFire) {
            double radius = fire.diameter;
            for (Sensor sensor : sensorList) {
                double distance = calculateDistance(fire.latitude, fire.longitude, sensor.getLatitude(), sensor.getLongitude());

                if (distance <= radius) {
                    long intensity = calculateIntensity(distance, radius);
                    sensorOnFireList.add(new SensorOnFire(sensor.getId(), intensity));
                }
            }
        }

        sendData(convertObjectToJson(sensorOnFireList));
    }

    public void sendData(String json) {
        System.out.println(json);
    }

    public void checkSensorsOnFire(){

    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * 6371;

        return distance * 1000;
    }

    public long calculateIntensity(double distance, double radius) {
        long nb = Math.round(radius) - Math.round(distance);
        long result = (nb * 100 / Math.round(radius)) / 10;
        return Math.round(result);
    }

    public static String convertObjectToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
