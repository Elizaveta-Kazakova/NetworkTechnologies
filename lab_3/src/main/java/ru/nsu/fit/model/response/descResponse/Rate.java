package ru.nsu.fit.model.response.descResponse;

public enum Rate {
    ZERO("0"),
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FIRST_H("1h"),
    SECOND_H("2h"),
    THIRD_H("3h");

    public final String rate;

    Rate(String rate) {
        this.rate = rate;
    }
}
