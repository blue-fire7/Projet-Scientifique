package fr.lespimpons.application.logic;

import fr.lespimpons.application.logic.dto.FireDto;
import fr.lespimpons.application.logic.internal.entity.FireImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogicManagement {

    private final ApplicationEventPublisher events;

    @Transactional
    public void fireEvent(FireImpl fire) {
        events.publishEvent(new FireDto(fire.getId(), fire.getCircle(), fire.getCreationDate(), fire.getExtinctionDate(), fire.getPower()));
    }

}
