package fr.lespimpons.simulator.entity;

import fr.lespimpons.application.logic.internal.entity.FireFighter;
import fr.lespimpons.application.pojo.geometry.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fire_station")
public class Station implements Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private Double longitude;

    @NotNull
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private Double latitude;

    @OneToMany(mappedBy = "fireStation")
    private Set<FireTruck> fireTrucks = new LinkedHashSet<>();

    @Override
    public Point getStationPosition() {
        return new Point(this.latitude.doubleValue(), this.longitude.doubleValue());
    }

}
