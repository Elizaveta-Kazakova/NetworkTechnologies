package ru.nsu.fit.model.response.weatherResponse;

public class Sys {
    private int type;
    private int id;
    private String country;
    private int sunrise;
    private int sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }
}
