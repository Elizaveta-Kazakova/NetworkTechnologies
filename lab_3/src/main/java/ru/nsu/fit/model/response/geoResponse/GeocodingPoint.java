package ru.nsu.fit.model.response.geoResponse;

public class GeocodingPoint {
    private final Double lat;
    private final Double lng;

    public GeocodingPoint() {
        lat = 0.0;
        lng = 0.0;
    }

    public GeocodingPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
