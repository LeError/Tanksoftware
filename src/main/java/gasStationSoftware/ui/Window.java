package gasStationSoftware.ui;

import gasStationSoftware.controller.Logic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Window
extends Application {

    private double screenOffsetX = 0;
    private double screenOffsetY = 0;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Startet die UI und legt verhalten der Anwendung fest
     * @param stage Übergebene Stage
     * @throws Exception
     * @author Robin Herder
     */
    @Override public void start(Stage stage)
    throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Window.fxml"));
        root.setOnMousePressed(event -> {
            screenOffsetX = event.getSceneX();
            screenOffsetY = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - screenOffsetX);
            stage.setY(event.getScreenY() - screenOffsetY);
        });

        Scene scene = new Scene(root, 1000, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("TANKWare");
        stage.setScene(scene);
        stage.show();
    }

}