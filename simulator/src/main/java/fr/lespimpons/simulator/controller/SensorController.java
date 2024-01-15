package fr.lespimpons.simulator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lespimpons.simulator.component.InterventionSingleton;
import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Sensor;
import fr.lespimpons.simulator.entity.SensorEvent;
import fr.lespimpons.simulator.object.Fire;
import fr.lespimpons.simulator.services.FireTruckService;
import fr.lespimpons.simulator.services.SensorService;
import fr.lespimpons.simulator.component.FireSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class SensorController {
    @Autowired
    private SensorService service;

    private FireTruckService fireTruckService;

    private final RestTemplate restTemplate;

    private final InterventionSingleton interventionSingleton;

    public SensorController(RestTemplate restTemplate, InterventionSingleton interventionSingleton) {
        this.restTemplate = restTemplate;
        this.interventionSingleton = interventionSingleton;
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
                else {
                    Boolean valid = true;
                    for (SensorOnFire sensorOnFire : sensorOnFireList){
                        if (sensorOnFire.id == sensor.getId()){
                            valid = false;
                        }
                    }
                    if (valid){
                        SensorEvent sensorEvent = service.findSensorEventBySensorId(sensor.getId());
                        if(sensorEvent != null && sensorEvent.getLevel() != 0){
                            sensorOnFireList.add(new SensorOnFire(sensor.getId(), 0));
                        }
                    }
                }
            }
        }
        for (SensorOnFire sensor : sensorOnFireList){
            System.out.println("Sensor : "+sensor.id+", "+sensor.intensity);
        }
        sendData(convertObjectToJson(sensorOnFireList));
    }

    @PostMapping("/send-data")
    public ResponseEntity<String> sendData(@RequestBody String json) {
        //Affichage du Json à envoyer
//        System.out.println("JSON data: " + json);

        //String url = "http://postman-echo.com/post";
        String url = "http://192.168.78.83:8080/api/data_capteur";

        // Créer un en-tête pour spécifier le type de contenu JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Créer une entité HTTP avec le corps JSON et l'en-tête
        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        // Envoyer la requête POST
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        // Traitement de la réponse
        String responseBody = responseEntity.getBody();
        System.out.println("Response from server: " + responseBody);

        return responseEntity;
    }

    @Transactional
    public void checkFires(List<Fire> listFire){
        //Liste des camions en intervention
        List<FireTruck> fireTruckList = interventionSingleton.getTrucks();
        for (FireTruck fireTruck : fireTruckList){
            System.out.println("FireTruck "+fireTruck.getId()+ " Lat : "+fireTruck.getLatitude()+" Lng : "+fireTruck.getLongitude());
        }

        for (Fire fire : listFire){
            for (FireTruck fireTruck : fireTruckList){
                if (isFireTruckInFire(fire, fireTruck)){
                    System.out.println("Camion !");
                    fire.setDiameter(fire.getDiameter() - fireTruck.getFireTruckType().getPowerFactor()*30);
                    System.out.println("Fire "+fire.getId()+" diameter : "+fire.getDiameter());
                }
            }
        }
    }

    public static boolean isFireTruckInFire(Fire fire, FireTruck fireTruck){
        double distance = calculateDistance(fireTruck.getLatitude(), fireTruck.getLongitude(), fire.getLatitude(), fire.getLongitude());
        return distance <= (75 + (double) fire.getDiameter());
    }

    public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        // Rayon moyen de la Terre en kilomètres
        double R = 6371.0;

        // Conversion des degrés en radians
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);

        // Calcul des différences de latitude et de longitude
        double dLat = lat2 - lat1;
        double dLng = lng2 - lng1;

        // Formule haversine
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLng / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance haversienne
        double distance = R * c;

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
