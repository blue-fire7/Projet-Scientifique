package fr.lespimpons.simulator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lespimpons.application.logic.internal.entity.Team;
import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.object.Fire;
import fr.lespimpons.simulator.services.SensorService;
import fr.lespimpons.simulator.component.FireSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173/")
public class SensorController {
    @Autowired
    private SensorService service;

    private final RestTemplate restTemplate;

    public SensorController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/sensor")
    public List<Sensor> findAll() {
        return service.findAll();
    }

    public record SensorOnFire(long id, long intensity) {
    }

    @PostMapping("add-fire")
    public void addFire(@RequestBody List<Fire> listFire){
        FireSingleton.getInstance().getFireList().addAll(listFire);
    }

    public void sensorsOnFire(@RequestBody List<Fire> listFire) {
        List<Sensor> sensorList = service.findAll();
        List<SensorOnFire> sensorOnFireList = new ArrayList<>();

        for (Fire fire : listFire) {
            double radius = fire.getDiameter();
            for (Sensor sensor : sensorList) {
                double distance = calculateDistance(fire.getLatitude(), fire.getLongitude(), sensor.getLatitude(), sensor.getLongitude());

                if (distance <= radius) {
                    long intensity = calculateIntensity(distance, radius);
                    sensorOnFireList.add(new SensorOnFire(sensor.getId(), intensity));
                }
            }
        }

        sendData(convertObjectToJson(sensorOnFireList));
    }

    @PostMapping("/send-data")
    public ResponseEntity<String> sendData(@RequestBody String json) {
        // Logique de traitement des données (à adapter selon vos besoins)
        System.out.println("JSON data: " + json);

        // Exemple de requête HTTP POST vers le localhost (ajustez l'URL en fonction de votre configuration)
        String url = "http://postman-echo.com/post"; // Remplacez par l'URL de votre endpoint
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, json, String.class);

        // Logique de traitement de la réponse (à adapter selon vos besoins)
        String responseBody = responseEntity.getBody();
        System.out.println("Response from server: " + responseBody);

        return responseEntity;
    }

    @Transactional
    public void checkFires(List<Fire> listFire){

        for (Fire fire : listFire){
            List<Intervention> interventionList = service.findInterventionByFireId(fire.getId());

            if (interventionList != null){
                for (Intervention intervention : interventionList){
                    int puissance = intervention.getFireTruck().getFireTruckType().getPowerFactor() * 10;
                    fire.setDiameter(fire.getDiameter() - puissance);
                }
            }
        }
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
        if (result == 0){
            result = 1;
        }
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
