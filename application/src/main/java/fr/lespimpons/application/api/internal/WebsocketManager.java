package fr.lespimpons.application.api.internal;

import fr.lespimpons.application.logic.internal.entity.Fire;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketManager {

    private final SimpMessagingTemplate messagingTemplate;

    public void updateFire(Fire fire) {
        messagingTemplate.convertAndSend("/topic/fire", "update");
    }

}
