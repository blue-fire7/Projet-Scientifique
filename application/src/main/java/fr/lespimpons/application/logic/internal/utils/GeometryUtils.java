package fr.lespimpons.application.logic.internal.utils;

import fr.lespimpons.application.pojo.geometry.Point;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeometryUtils {


    private static final int EARTH_RADIUS = 6371;


    public static double calculateDistance(Point start, Point end) {
        double lat1Rad = Math.toRadians(start.getLatitude());
        double lat2Rad = Math.toRadians(end.getLatitude());
        double lon1Rad = Math.toRadians(start.getLongitude());
        double lon2Rad = Math.toRadians(end.getLongitude());

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);

        return Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }

}
