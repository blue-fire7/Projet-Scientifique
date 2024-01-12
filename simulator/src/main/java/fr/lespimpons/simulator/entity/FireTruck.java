package fr.lespimpons.simulator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fire_truck")
public class FireTruck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fire_station_id", nullable = false)
    private Station fireStation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fire_truck_type_id", nullable = false)
    private FireTruckType fireTruckType;

    @NotNull
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private Double longitude;

    @NotNull
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private Double latitude;

    @OneToMany(mappedBy = "fireTruck")
    private Set<Intervention> interventions = new LinkedHashSet<>();

}
