package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class InterventionId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @NotNull
    @Column(name = "fire_truck_id", nullable = false)
    private Long fireTruckId;

    @NotNull
    @Column(name = "fire_id", nullable = false)
    private Long fireId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InterventionId entity = (InterventionId) o;
        return Objects.equals(this.fireTruckId, entity.fireTruckId) &&
                Objects.equals(this.teamId, entity.teamId) &&
                Objects.equals(this.fireId, entity.fireId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fireTruckId, teamId, fireId);
    }

}