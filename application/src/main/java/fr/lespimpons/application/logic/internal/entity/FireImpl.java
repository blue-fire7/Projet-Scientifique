package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Circle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fire")
@Getter
@Setter
public class FireImpl implements Fire {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;


    @Override
    public void extinguishFire(FireTruck fireTruck) {
        //TODO
    }

    @Override
    public Fire propagateFire() {
        //TODO
        return null;
    }
}
