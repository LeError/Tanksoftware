package gasStationSoftware.ui;

import gasStationSoftware.controller.Logic;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Window extends Application {

    private Logic logic;

    private double screenOffsetX = 0;
    private double screenOffsetY = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logic = new Logic(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Window.fxml"));
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                screenOffsetX = event.getSceneX();
                screenOffsetY = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - screenOffsetX);
                stage.setY(event.getScreenY() - screenOffsetY);
            }
        });

        Scene scene = new Scene(root, 1000, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("TANKWare");
        stage.setScene(scene);
        stage.show();
    }

}
