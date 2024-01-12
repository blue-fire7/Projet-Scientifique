package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "sensor_event")
@NoArgsConstructor
@AllArgsConstructor
public class SensorEventImpl implements Event {
    @EmbeddedId
    private SensorEventId id;


    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @MapsId("fireId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fire_id")
    private FireImpl fireImpl;

    @MapsId("sensorId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    private SensorImpl sensorImpl;

    @Override
    public LocalDateTime updateAt() {
        return id.getUpdateAt();
    }
}