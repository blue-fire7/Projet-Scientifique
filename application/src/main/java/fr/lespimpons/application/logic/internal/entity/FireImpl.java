package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fire")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FireImpl implements Fire {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "fire")
    private Set<Intervention> interventions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "fireImpl")
    private Set<SensorEventImpl> sensorEventImpls = new LinkedHashSet<>();


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
