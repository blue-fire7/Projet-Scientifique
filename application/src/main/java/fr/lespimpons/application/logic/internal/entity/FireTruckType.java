package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fire_truck_type")
public class FireTruckType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "label", nullable = false, length = 50)
    private String type;

    @NotNull
    @Column(name = "speed", nullable = false)
    private Integer speed;

    @OneToMany(mappedBy = "fireTruckType")
    private Set<FireTruck> fireTrucks = new LinkedHashSet<>();

    @NotNull
    @Column(name = "power_factor", nullable = false)
    private Integer powerFactor;

}