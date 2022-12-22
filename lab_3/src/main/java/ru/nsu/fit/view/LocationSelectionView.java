package ru.nsu.fit.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import org.apache.commons.lang3.StringUtils;
import ru.nsu.fit.model.response.geoResponse.GeoResponse;
import ru.nsu.fit.model.response.geoResponse.GeocodingLocation;
import ru.nsu.fit.model.response.geoResponse.GeocodingPoint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationSelectionView {
    private static final String DELIMITER_IN_ONE_RECORD = ", ";
    private static final String DELIMITER_FOR_RECORDS_IN_FILE = "\n";
    private static final String DELIMITER_FOR_COORDINATES = ";";
    private static final String NAME_OF_FILE_WITH_LOCATIONS = "src/main/java/ru/nsu/fit/model/locations";
    private static final String NAME_OF_FILE_WITH_COORDS = "src/main/java/ru/nsu/fit/model/coordinates";
    private static final int INDEX_FOR_LAT = 0;
    private static final int INDEX_FOR_LNG = 1;

    private final ObservableList<String> list = FXCollections.observableArrayList();
    public ArrayList<String> locationList;
    public ArrayList<GeocodingPoint> coordinates;

    protected GeoResponse geoResponse;

    @FXML
    protected ChoiceBox<String> selectionBox;

    private ArrayList<String> downloadLocationsFile() {
        ArrayList<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NAME_OF_FILE_WITH_LOCATIONS))) {
            String newLine;
            while ((newLine = reader.readLine()) != null) {
                fileContent.add(newLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    private ArrayList<GeocodingPoint> downloadCoordsFile() {
        ArrayList<GeocodingPoint> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NAME_OF_FILE_WITH_COORDS))) {
            String newLine;
            while ((newLine = reader.readLine()) != null) {
                String[] coords = newLine.split(DELIMITER_FOR_COORDINATES);
                fileContent.add(new GeocodingPoint(Double.parseDouble(coords[INDEX_FOR_LAT]),
                        Double.parseDouble(coords[INDEX_FOR_LNG])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    private void updateLocationsFileContent(ArrayList<String> fileContent) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(NAME_OF_FILE_WITH_LOCATIONS),
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String s : fileContent) {
                writer.write(s + DELIMITER_FOR_RECORDS_IN_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCoordsFileContent(ArrayList<GeocodingPoint> fileContent) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(NAME_OF_FILE_WITH_COORDS),
                StandardOpenOption.TRUNCATE_EXISTING)) {
            for (GeocodingPoint point : fileContent) {
                writer.write(point.getLat() + DELIMITER_FOR_COORDINATES + point.getLng()
                        + DELIMITER_FOR_RECORDS_IN_FILE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String formString(GeocodingLocation location) {
        return Stream.of(location.getCountry(), location.getState(), location.getCity(), location.getName(),
                        location.getStreet())
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.joining(DELIMITER_IN_ONE_RECORD));

    }

    protected ArrayList<String> formStringsFromResponse(GeoResponse geoResponse) {
        int numOfLocation = 0;
        ArrayList<String> locationList = new ArrayList<>();
        while (numOfLocation < geoResponse.getHits().size()) {
            String locationDesc = formString(geoResponse.getHits().get(numOfLocation));
            locationList.add(locationDesc);
            ++numOfLocation;
        }
        return locationList;
    }

    public void setData(GeoResponse geoResponse) {
        this.geoResponse = geoResponse;
        this.coordinates = geoResponse.getListOfCoordinates();
        locationList = formStringsFromResponse(geoResponse);
        updateLocationsFileContent(locationList);
        updateCoordsFileContent(coordinates);
        list.clear();
        list.addAll(locationList);
        selectionBox.getItems().clear();
        selectionBox.getItems().addAll(list);
    }

    public void initView() {
        locationList = downloadLocationsFile();
        coordinates = downloadCoordsFile();
        list.addAll(locationList);
        selectionBox.getItems().addAll(list);
    }

    public ArrayList<String> getLocationList() {
        return locationList;
    }

    public ArrayList<GeocodingPoint> getCoordinates() {
        return coordinates;
    }
}
