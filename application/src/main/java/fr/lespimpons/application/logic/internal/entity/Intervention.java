package fr.lespimpons.application.logic.internal.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "intervention")
public class Intervention {
    @EmbeddedId
    private InterventionId id;


    @MapsId("fireTruckId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fire_truck_id")
    private FireTruck fireTruck;

    @MapsId("fireId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fire_id")
    private FireImpl fire;

    @MapsId("teamId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


/*
 TODO [JPA Buddy] create field to map the 'path' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "path", columnDefinition = "path(0, 0)")
    private java.lang.Object path;
*/
}