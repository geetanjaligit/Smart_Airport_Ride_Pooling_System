package com.airport.ridepooling.util;

//This class uses the "Haversine Formula" to calculate the real distance in Kilometers between two points (Latitude/Longitude).

public class DistanceUtils {

    // Radius of the earth in kilometers
    private static final double EARTH_RADIUS = 6371.0;

    // Calculates the distance between two points in Kilometers.
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
