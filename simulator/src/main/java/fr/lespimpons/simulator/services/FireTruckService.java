package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.entity.Intervention;
import fr.lespimpons.simulator.repository.FireTruckRepository;
import fr.lespimpons.simulator.repository.InterventionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class FireTruckService {
    private final FireTruckRepository repository;
    private final WebClient webClient;

    private final RestTemplate restTemplate;

    public FireTruckService(FireTruckRepository repository, RestTemplate restTemplate){
        this.repository = repository;
        this.webClient = WebClient.create();
        this.restTemplate = restTemplate;
    }

    public List<FireTruck> findAll() {
        return repository.findAll();
    }

    public List<FireTruck> findFireTrucksInIntervention(){
        return repository.findFireTruckInIntervention();
    }

    public void sendFireTruckData(String fireTruckListString){
//        this.webClient.post().uri("http://192.168.78.83:8080/api/data_camion").bodyValue("ouai c'est jerem").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve().bodyToMono(String.class).block();


        String url = "http://192.168.78.83:8080/api/data_camion";
        //String url = "http://192.168.27.83:8000/api/data_capteur";

        // Créer un en-tête pour spécifier le type de contenu JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Créer une entité HTTP avec le corps JSON et l'en-tête
        HttpEntity<String> entity = new HttpEntity<>(fireTruckListString, headers);

        // Envoyer la requête POST
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

        // Traitement de la réponse
        String responseBody = responseEntity.getBody();
        //System.out.println("Response from server: " + responseBody);
    }
}
