package fr.lespimpons.simulator.entity;

import fr.lespimpons.application.logic.internal.entity.Event;
import fr.lespimpons.application.logic.internal.entity.SensorImpl;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor_event")
public class SensorEvent {
    @EmbeddedId
    private SensorEventId id;

    @MapsId("sensorId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @MapsId("fireId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fire_id")
    private Fire fire;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

}