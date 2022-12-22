package ru.nsu.fit.model;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.nsu.fit.model.response.descResponse.DescResponse;
import ru.nsu.fit.model.response.geoResponse.GeocodingPoint;
import ru.nsu.fit.model.response.placesResponse.PlacesElement;
import ru.nsu.fit.model.response.weatherResponse.WeatherResponse;
import ru.nsu.fit.observerPattern.Observable;

public class Client extends Observable {
    private static final int TIME_TO_WAIT_FOR_SEND_IN_MILISECONDS = 60 * 100;
    private static final int NUM_OF_POSSIBLE_REQUESTS_WITHIN_TIME_LIMIT = 6;
    private static final int EMPTY_SIZE = 0;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final URICreator uriCreator = new URICreator();

    private boolean isWeatherReady = false;
    private boolean isPlacesReady = false;
    private boolean isDescReady = false;

    private WeatherResponse weatherResponse;
    private ArrayList<PlacesElement> placesResponse;
    private final ArrayList<DescResponse> descResponses = new ArrayList<>();
    private long timeOfLastSending;
    private int numOfSendedInTimeInterval = 0;

    private void setDescResponses(String descFuture) {
        System.out.println(descFuture);
        DescResponse response = gson.fromJson(descFuture, DescResponse.class);
        descResponses.add(response);
        isDescReady = true;
        notifyObserver();
    }

    private void setWeatherResponse(String weatherFuture) {
        weatherResponse = gson.fromJson(weatherFuture, WeatherResponse.class);
        isWeatherReady = true;
        notifyObserver();
    }

    private void setPlacesResponse(String placesFuture) {
        TypeToken<ArrayList<PlacesElement>> collectionType = new TypeToken<>(){};
        placesResponse = gson.fromJson(placesFuture, collectionType);
        isPlacesReady = true;
        notifyObserver();
    }

    private String setPlacesAndContinue(String placesFuture) {
        setPlacesResponse(placesFuture);
        notifyObserver();
        return placesFuture;
    }

    private void sendRequestDescription(String xid) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriCreator.getDescURI(xid))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::setDescResponses);
        timeOfLastSending = System.currentTimeMillis();
    }

    public CompletableFuture<String> getGeoLocation(String location) throws URISyntaxException {
        String encodeLocation = java.net.URLEncoder.encode(location, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriCreator.getGeoURI(encodeLocation))
                .build();
        timeOfLastSending = System.currentTimeMillis();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

    }

    public void sendRequestForWeather(GeocodingPoint point) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriCreator.getWeatherURI(point))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::setWeatherResponse);
        timeOfLastSending = System.currentTimeMillis();
    }

    public void sendRequestPlaces(GeocodingPoint point) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uriCreator.getPlacesURI(point))
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::setPlacesAndContinue)
                .thenAccept(this::sendRequestDescriptions);
        timeOfLastSending = System.currentTimeMillis();
    }

    public void sendRequestDescriptions(String placesFuture) {
        if (placesResponse.size() == EMPTY_SIZE) {
            isDescReady = true;
            return;
        }
        for (int numOfPlace = 0; numOfPlace < placesResponse.size(); ++numOfPlace) {
            long waitedTime = System.currentTimeMillis() - timeOfLastSending;
            if (waitedTime < TIME_TO_WAIT_FOR_SEND_IN_MILISECONDS &&
                    (numOfSendedInTimeInterval + 1) > NUM_OF_POSSIBLE_REQUESTS_WITHIN_TIME_LIMIT) {
                try {
                    TimeUnit.MILLISECONDS.sleep(TIME_TO_WAIT_FOR_SEND_IN_MILISECONDS - waitedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                numOfSendedInTimeInterval = 0;
            }
            try {
                sendRequestDescription(placesResponse.get(numOfPlace).getXid());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            ++numOfSendedInTimeInterval;
        }
    }

    public ArrayList<PlacesElement> getPlacesResponse() {
        return placesResponse;
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public ArrayList<DescResponse> getDescResponses() {
        return descResponses;
    }

    public boolean isWeatherReady() {
        return isWeatherReady;
    }

    public boolean isPlacesReady() {
        return isPlacesReady;
    }

    public boolean isDescReady() {
        return isDescReady;
    }
}
