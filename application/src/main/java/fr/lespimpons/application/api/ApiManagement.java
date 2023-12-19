package fr.lespimpons.application.api;

import fr.lespimpons.application.logic.dto.FireDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApiManagement {

    private final ApplicationEventPublisher events;

    @Async
    @TransactionalEventListener
    public void receiveFireEvent(FireDto fire) {
    log.info("Received fire event: {}", fire);
    }

}
