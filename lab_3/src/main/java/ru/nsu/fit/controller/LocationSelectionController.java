package ru.nsu.fit.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ru.nsu.fit.App;
import ru.nsu.fit.model.Client;
import ru.nsu.fit.model.response.geoResponse.GeocodingPoint;
import ru.nsu.fit.view.LocationSelectionView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class LocationSelectionController extends LocationSelectionView {
    private static final String MESSAGE_OF_EMPTY_LOCATION = "Please, choose one of the locations";
    private static final String LOCATION_DESCRIPTION_VIEW = "/ru/nsu/fit/locationDescription.fxml";
    private static final String EMPTY_STR = "";

    private final Client client = new Client();

    @FXML
    private Label errorLabel;

    private GeocodingPoint findCoordinates(String location) {
        int numOfLocation = 0;
        ArrayList<String> responseArray = getLocationList();
        while (numOfLocation < responseArray.size()) {
            if (Objects.equals(location, responseArray.get(numOfLocation))) {
                return getCoordinates().get(numOfLocation);
            }
            ++numOfLocation;
        }
        return null;
    }

    private void clientActionsOnEvent(GeocodingPoint coordinates) throws URISyntaxException {
        client.sendRequestForWeather(coordinates);
        client.sendRequestPlaces(coordinates);
    }

    @FXML
    public void initialize() {
        initView();
    }

    @FXML
    public void showLocationDescription(MouseEvent event) {
        if (selectionBox.getSelectionModel().isEmpty()) {
            errorLabel.setText(MESSAGE_OF_EMPTY_LOCATION);
            return;
        }
        errorLabel.setText(EMPTY_STR);
        String location = selectionBox.getValue();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(LOCATION_DESCRIPTION_VIEW));
        try {
            Parent root =  loader.load();
            LocationDescriptionController descriptionController = loader.getController();
            descriptionController.setData(client);

            GeocodingPoint coordinates = findCoordinates(location);
            clientActionsOnEvent(coordinates);

            App.setNewScene(root);
        } catch (IOException e) {
            errorLabel.setText(MESSAGE_OF_EMPTY_LOCATION);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}