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
public class SensorEvent implements Event {
    @EmbeddedId
    private SensorEventId id;

    @MapsId("sensorId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    private SensorImpl sensor;

    @NotNull
    @Column(name = "level", nullable = false)
    private Integer level;

    @Override
    public LocalDateTime updateAt() {
        return id.getUpdateAt();
    }
}