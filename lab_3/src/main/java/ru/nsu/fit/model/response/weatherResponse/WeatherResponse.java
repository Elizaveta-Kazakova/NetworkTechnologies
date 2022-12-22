package ru.nsu.fit.model.response.weatherResponse;

public class WeatherResponse {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private MainWeather main;
    private double visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    private int dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;
    private String error;

    public Coord getCoord() {
        return coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public String getError() {
        return error;
    }

    public MainWeather getMain() {
        return main;
    }
}
