package fr.lespimpons.application.pojo.geometry;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Circle {

    private Point center;
    private Double radiusInMeters;

    public Circle(Point center, double radiusInMeters) {
        this.center = center;
        this.radiusInMeters = radiusInMeters;
    }

    public boolean contains(Point point) {
        return this.center.distanceTo(point) <= this.radiusInMeters;
    }

    public Double getArea() {
        return Math.PI * Math.pow(this.radiusInMeters, 2);
    }

    public void reduceByArea(Double areaInSquareMeters) {
        this.radiusInMeters = Math.sqrt((this.getArea() - areaInSquareMeters) / Math.PI);
    }

    public void expandByArea(Double areaInSquareMeters) {
        this.radiusInMeters = Math.sqrt((this.getArea() + areaInSquareMeters) / Math.PI);
    }

}
