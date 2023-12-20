package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SensorEventId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "sensor_id", nullable = false)
    private Long sensorId;

    @NotNull
    @Column(name = "fire_id", nullable = false)
    private Long fireId;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SensorEventId entity = (SensorEventId) o;
        return Objects.equals(this.updateAt, entity.updateAt) &&
                Objects.equals(this.fireId, entity.fireId) &&
                Objects.equals(this.sensorId, entity.sensorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateAt, fireId, sensorId);
    }

}