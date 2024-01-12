package fr.lespimpons.simulator.entity;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fire_truck_id")
    private FireTruck fireTruck;

    @MapsId("fireId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fire_id")
    private Fire fire;

    @MapsId("teamId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;
}