package fr.lespimpons.application.logic.internal.entity;

import fr.lespimpons.application.pojo.geometry.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fire_truck")
@Getter
@Setter
public class FireTruck implements Vehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fire_station_id", nullable = false)
    private StationImpl fireStation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fire_truck_type_id", nullable = false)
    private FireTruckType fireTruckType;

    @OneToMany(mappedBy = "fireTruck")
    private Set<Intervention> interventions = new LinkedHashSet<>();

    @Column(name = "longitude", precision = 3, scale = 20)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 3, scale = 20)
    private BigDecimal latitude;

    public void move(Point destination) {
        this.latitude = getLatitude();
        this.longitude = getLongitude();
    }

    @Override
    public void returnToBase() {
        this.move(fireStation.getStationPosition());
    }

}
