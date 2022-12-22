package ru.nsu.fit.model.response.geoResponse;

import java.util.ArrayList;

public class GeoResponse {
    private final ArrayList<GeocodingLocation> hits = new ArrayList<>();

    public ArrayList<GeocodingLocation> getHits() {
        return hits;
    }

    public ArrayList<GeocodingPoint> getListOfCoordinates() {
        ArrayList<GeocodingPoint> geocodingPoints = new ArrayList<>();
        for (GeocodingLocation geocodingLocation : hits) {
            geocodingPoints.add(geocodingLocation.getPoint());
        }
        return geocodingPoints;
    }
}
