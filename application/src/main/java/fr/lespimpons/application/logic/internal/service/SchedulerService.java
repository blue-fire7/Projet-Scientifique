package fr.lespimpons.application.logic.internal.service;

import fr.lespimpons.application.logic.LogicManagement;
import fr.lespimpons.application.logic.dto.SensorDto;
import fr.lespimpons.application.logic.internal.entity.FireImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {


    private final LogicManagement logicManagement;

    @PostConstruct
    public void init() {
        log.info("bordel");
        System.out.println("bordel");
    }

    @Scheduled(fixedDelayString = "${REFRESH_DELAY}")
    public void update() {
        logicManagement.sendSensorEvent(new SensorDto(1L, 4.903327, 45.785836, 3));
        System.out.println("update");
    }
}
