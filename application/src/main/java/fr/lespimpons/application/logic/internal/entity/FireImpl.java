package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Circle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;
import java.time.LocalDate;
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
    private Set<SensorEvent> sensorEvents = new LinkedHashSet<>();


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
