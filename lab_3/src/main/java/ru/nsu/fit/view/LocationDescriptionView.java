package ru.nsu.fit.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.apache.commons.lang3.StringUtils;
import ru.nsu.fit.model.Client;
import ru.nsu.fit.model.response.descResponse.DescResponse;
import ru.nsu.fit.model.response.geoResponse.GeocodingPoint;
import ru.nsu.fit.model.response.placesResponse.PlacesElement;
import ru.nsu.fit.model.response.placesResponse.PlacesResponse;
import ru.nsu.fit.model.response.weatherResponse.MainWeather;
import ru.nsu.fit.model.response.weatherResponse.WeatherResponse;
import ru.nsu.fit.observerPattern.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationDescriptionView implements Observer {
    private static final String EMPTY_SIZE_DESCRIPTION = "no description";
    private static final String EMPTY_SIZE_PLACES = "no places";
    private static final String EMPTY_STR = "";
    private static final String DELIMITER = ", ";
    private static final int EMPTY_SIZE = 0;

    protected Client client;
    protected GeocodingPoint location;

    @FXML
    private TextArea attractionDescriptions;

    @FXML
    private TextArea attractionList;

    @FXML
    private TextArea weatherDescription;

    @FXML
    private ImageView weatherImage;

    private String getFormattedTextAboutWeather() {
        if (!client.isWeatherReady()) return EMPTY_STR;
        WeatherResponse weatherResponse = client.getWeatherResponse();
        MainWeather mainWeather = weatherResponse.getMain();
        return "temp: " + mainWeather.getTemp() + "\nfeels like: " + mainWeather.getFeels_like()
                        + "\nhumidity: " + mainWeather.getHumidity() + "\npressure: " + mainWeather.getPressure();
    }

    private String getFormattedTextAboutPlaces() {
        if (!client.isPlacesReady()) return EMPTY_STR;
        ArrayList<PlacesElement> placesResponse = client.getPlacesResponse();
        if (placesResponse.size() == EMPTY_SIZE) {
            return EMPTY_SIZE_PLACES;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int numOfPlace = 0; numOfPlace < placesResponse.size(); ++numOfPlace) {
            stringBuilder.append(numOfPlace + 1).append(") ");
            if (!Objects.equals(placesResponse.get(numOfPlace).getName(), EMPTY_STR)) {
                stringBuilder.append(placesResponse.get(numOfPlace).getName()).append(":\n");
            }
            if (placesResponse.get(numOfPlace).getKind() != null) {
                stringBuilder.append(placesResponse.get(numOfPlace).getKind());
            } else {
                stringBuilder.append("no kinds");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private String getFormattedTextWithDescriptions() {
        if (!client.isDescReady()) return EMPTY_STR;
        ArrayList<DescResponse> descResponses = client.getDescResponses();
        if (descResponses.size() == EMPTY_SIZE) {
            return EMPTY_SIZE_DESCRIPTION;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int numOfDesc = 0; numOfDesc < descResponses.size(); ++numOfDesc) {
            DescResponse usingDescResponse = descResponses.get(numOfDesc);
            stringBuilder.append(numOfDesc + 1).append(") ");
            stringBuilder.append("Rate: ").append(usingDescResponse.getRate()).append(DELIMITER);
            if (usingDescResponse.getAddress() != null) {
                stringBuilder
                        .append(Stream.of(
                                        usingDescResponse.getAddress().getCity(),
                                        usingDescResponse.getAddress().getRoad(),
                                        usingDescResponse.getAddress().getHouse())
                                .filter(StringUtils::isNotEmpty)
                                .collect(Collectors.joining(DELIMITER)));
            }
            if (usingDescResponse.getInfo() != null
                    && usingDescResponse.getInfo().getDescr() != null) {
                stringBuilder.append(" description:").append(usingDescResponse.getInfo().getDescr());
            }

            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void setData(Client client) {
        this.client = client;
        client.addObserver(this);
    }

    @Override
    public void update() {
        String places = getFormattedTextAboutPlaces();
        String desc = getFormattedTextWithDescriptions();
        String weather = getFormattedTextAboutWeather();
        Platform.runLater(() -> attractionList.setText(places));
        Platform.runLater(() -> attractionDescriptions.setText(desc));
        Platform.runLater(() -> weatherDescription.setText(weather));
    }
}
