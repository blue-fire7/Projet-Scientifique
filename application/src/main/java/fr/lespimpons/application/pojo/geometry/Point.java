package fr.lespimpons.application.pojo.geometry;

import java.util.Objects;

public class Point {

    private final double longitude;
    private final double latitude;

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(longitude, point.longitude) == 0 && Double.compare(latitude, point.latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    public double distanceTo(Point point) {
        return Math.sqrt(Math.pow(this.longitude - point.longitude, 2) + Math.pow(this.latitude - point.latitude, 2));
    }

    public Circle toCircle(Double diameterInMeters) {
        return
                new Circle(this, diameterInMeters);
    }

}
