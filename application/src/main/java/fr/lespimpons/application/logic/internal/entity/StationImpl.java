package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fire_station")
@Getter
@Setter
public class StationImpl implements Station{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @OneToMany(mappedBy = "fireStation")
    private Set<FireFighter> fireFighters = new LinkedHashSet<>();

    @OneToMany(mappedBy = "fireStation")
    private Set<FireTruck> fireTrucks = new LinkedHashSet<>();

    @Override
    public Point getStationPosition() {
        return new Point(this.latitude.doubleValue(), this.longitude.doubleValue());
    }

}
