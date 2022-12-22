module ru.nsu.fit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires geojson.jackson;
    requires org.apache.httpcomponents.httpclient;

    opens ru.nsu.fit to javafx.fxml;
    exports ru.nsu.fit;
    opens ru.nsu.fit.model.response.geoResponse to com.google.gson;
    opens ru.nsu.fit.model.response.placesResponse to com.google.gson;
    opens ru.nsu.fit.model.response.weatherResponse to com.google.gson;
    opens ru.nsu.fit.model.response.descResponse to com.google.gson;
    opens ru.nsu.fit.view to javafx.fxml;
    opens ru.nsu.fit.controller to javafx.fxml;
    exports ru.nsu.fit.controller to javafx.fxml;
}
