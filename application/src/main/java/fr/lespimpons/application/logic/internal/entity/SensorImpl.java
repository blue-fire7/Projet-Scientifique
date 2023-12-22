package fr.lespimpons.application.logic.internal.entity;

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
@Table(name = "sensor")
public class SensorImpl implements Sensor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @OneToMany(mappedBy = "sensorImpl")
    private Set<SensorEvent> sensorEvents = new LinkedHashSet<>();


    @Override
    public Point getPosition() {
        return new Point(this.longitude.doubleValue(), this.latitude.doubleValue());
    }
}
