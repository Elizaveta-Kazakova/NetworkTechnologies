package ru.nsu.fit.model;

import org.apache.http.client.utils.URIBuilder;
import ru.nsu.fit.model.response.geoResponse.GeocodingPoint;

import java.net.URI;
import java.net.URISyntaxException;

public class URICreator {
    private static final String GEO_SCHEME = "https";
    private static final String GEO_PATH = "//graphhopper.com/api/1/geocode";
    private static final String WEATHER_SCHEME = "http";
    private static final String WEATHER_PATH = "//api.openweathermap.org/data/2.5/weather";
    private static final String PLACES_SCHEME = "http";
    private static final String PLACES_PATH = "//api.opentripmap.com/0.1/en/places/radius";
    private static final String DESC_SCHEME = "http";
    private static final String DESC_PATH = "//api.opentripmap.com/0.1/en/places/xid/";
    private static final String LOCATION_IDENTIFIER = "q";
    private static final String LONGITUDE_IDENTIFIER = "lon";
    private static final String LATITUDE_IDENTIFIER = "lat";
    private static final String API_GEOCODE_KEY = "fb576ed1-cdcf-4492-8203-52df4424f694";
    private static final String API_WEATHER_KEY = "d9727a9190ce379748163b6562599ddc";
    private static final String API_TRIP_KEY = "5ae2e3f221c38a28845f05b6fee4fb8a2ef7c45e1adaf24cdc80886c";
    private static final String KEY_WEATHER_IDENTIFIER = "appid";
    private static final String KEY_GEOCODE_IDENTIFIER = "key";
    private static final String KEY_TRIP_IDENTIFIER = "apikey";
    private static final String RADIUS_IDENTIFIER = "radius";
    private static final int RADIUS = 500;
    private static final String FORMAT_IDENTIFIER = "format";
    private static final String FORMAT = "json";



    public URI getGeoURI(String encodeLocation) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(GEO_SCHEME);
        uriBuilder.setPath(GEO_PATH);
        uriBuilder.addParameter(LOCATION_IDENTIFIER, encodeLocation);
        uriBuilder.addParameter(KEY_GEOCODE_IDENTIFIER, API_GEOCODE_KEY);
        return uriBuilder.build();
    }

    public URI getWeatherURI(GeocodingPoint point) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(WEATHER_SCHEME);
        uriBuilder.setPath(WEATHER_PATH);
        uriBuilder.addParameter(LATITUDE_IDENTIFIER, String.valueOf(point.getLat()));
        uriBuilder.addParameter(LONGITUDE_IDENTIFIER, String.valueOf(point.getLng()));
        uriBuilder.addParameter(KEY_WEATHER_IDENTIFIER, API_WEATHER_KEY);
        return uriBuilder.build();
    }

    public URI getPlacesURI(GeocodingPoint point) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(PLACES_SCHEME);
        uriBuilder.setPath(PLACES_PATH);
        uriBuilder.addParameter(LONGITUDE_IDENTIFIER, String.valueOf(point.getLng()));
        uriBuilder.addParameter(LATITUDE_IDENTIFIER, String.valueOf(point.getLat()));
        uriBuilder.addParameter(FORMAT_IDENTIFIER, FORMAT);
        uriBuilder.addParameter(RADIUS_IDENTIFIER, String.valueOf(RADIUS));
        uriBuilder.addParameter(KEY_TRIP_IDENTIFIER, API_TRIP_KEY);
        return uriBuilder.build();
    }

    public URI getDescURI(String xid) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme(DESC_SCHEME);
        uriBuilder.setPath(DESC_PATH + xid);
        uriBuilder.addParameter(FORMAT_IDENTIFIER, FORMAT);
        uriBuilder.addParameter(RADIUS_IDENTIFIER, String.valueOf(RADIUS));
        uriBuilder.addParameter(KEY_TRIP_IDENTIFIER, API_TRIP_KEY);
        return uriBuilder.build();
    }
}
