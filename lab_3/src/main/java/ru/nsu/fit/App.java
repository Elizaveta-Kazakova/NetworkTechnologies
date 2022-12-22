package ru.nsu.fit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    private static final String NAME_OF_APP = "Geographic advisor";
    private static final String ICON_IMAGE = "/ru/nsu/fit/images/geoAdvisorIcon.png";
    private static final String START_VIEW = "primary";
    private static final int WIDTH_OF_WINDOW = 900;
    private static final int LENGTH_OF_WINDOW = 600;

    private static Stage stage;

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) {
        App.stage = stage;
        stage.setTitle(NAME_OF_APP);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(ICON_IMAGE))));
        stage.centerOnScreen();
        Scene scene = null;
        try {
            scene = new Scene(loadFXML(START_VIEW), WIDTH_OF_WINDOW, LENGTH_OF_WINDOW);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setNewScene(Parent root) {
        Scene scene = new Scene(root, WIDTH_OF_WINDOW , LENGTH_OF_WINDOW);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}