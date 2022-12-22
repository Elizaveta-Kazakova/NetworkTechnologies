package ru.nsu.fit.controller;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.nsu.fit.App;
import ru.nsu.fit.model.Client;
import ru.nsu.fit.model.response.geoResponse.GeoResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class StartWindowController {
    private static final String LOCATION_SELECTION_VIEW = "/ru/nsu/fit/locationSelection.fxml";
    private static final String MESSAGE_OF_ERROR = "Sorry, we have some troubles. Please, try again.";
    private static final String MESSAGE_OF_EMPTY_LOCATION = "Please, enter location";
    private static final String MESSAGE_OF_NON_EXISTENT_LOCATION = "There are no such location.";
    private static final String WHITESPACE_CHARACTERS = "\\s+";
    private static final String EMPTY_STR = "";
    private static final int LENGTH_OF_EMPTY_LIST = 0;

    private final Gson gson = new Gson();

    private Parent root;
    private final Client client = new Client();

    @FXML
    private TextField locationField;

    @FXML
    private Label errorLabel;

    @FXML
    void showLocations(MouseEvent event) {
        String text = locationField.getText();
        if (text.replaceAll(WHITESPACE_CHARACTERS,EMPTY_STR).equals(EMPTY_STR)) {
            errorLabel.setText(MESSAGE_OF_EMPTY_LOCATION);
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(LOCATION_SELECTION_VIEW));
        try {
            root = loader.load();
            LocationSelectionController selectionController = loader.getController();

            CompletableFuture<String> future = client.getGeoLocation(locationField.getText());
            GeoResponse geoResponse = gson.fromJson(future.get(), GeoResponse.class);
            if(geoResponse.getHits().size() == LENGTH_OF_EMPTY_LIST) {
                errorLabel.setText(MESSAGE_OF_NON_EXISTENT_LOCATION);
                return;
            }

            selectionController.setData(geoResponse);
            App.setNewScene(root);
        } catch (IOException | ExecutionException | InterruptedException | URISyntaxException e) {
            errorLabel.setText(MESSAGE_OF_ERROR);
            e.printStackTrace();
        }
    }

}
