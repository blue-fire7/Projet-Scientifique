package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "intervention")
public class Intervention {
    @EmbeddedId
    private InterventionId id;




    @MapsId("fireTruckId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fire_truck_id")
    private FireTruck fireTruck;

/*
 TODO [JPA Buddy] create field to map the 'path' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "path", columnDefinition = "path(0, 0)")
    private java.lang.Object path;
*/
}