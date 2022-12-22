package ru.nsu.fit.model.response.geoResponse;


public class GeocodingLocation {
    private GeocodingPoint point = new GeocodingPoint();
    private String osm_id;
    private String osm_type;
    private String osm_key;
    private String name;
    private String country;
    private String city;
    private String state;
    private String street;
    private String housenumber;
    private String postcode;

    public GeocodingPoint getPoint() {
        return point;
    }

    public String getOsm_id() {
        return osm_id;
    }

    public String getOsm_type() {
        return osm_type;
    }

    public String getOsm_key() {
        return osm_key;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public String getPostcode() {
        return postcode;
    }
}
