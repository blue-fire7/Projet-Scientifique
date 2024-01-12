package fr.lespimpons.simulator.services;

import fr.lespimpons.simulator.entity.FireTruck;
import fr.lespimpons.simulator.object.Fire;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public void updateFireList(List<Fire> fireList) {
        messagingTemplate.convertAndSend("/topic/update/fires", fireList);
    }

    public void sendTruckList(List<FireTruck> truckList) {
        messagingTemplate.convertAndSend("/topic/update/trucks", truckList);
    }

}
