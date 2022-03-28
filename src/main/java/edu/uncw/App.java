package edu.uncw;
// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy

import edu.uncw.icons.Icon;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main class of the Application and entry point for loading the GUI.
 */
public class App extends Application {
    private static Stage primaryStage;

    /**
     * The primary Stage of the App's GUI.
     * @return Main Stage of the GUI.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        App.primaryStage = primaryStage;
        primaryStage.setTitle("Minesweeper FX");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("AppIcon.png")));
        Scene scene = new Scene(root);
        primaryStage.setWidth(580);
        primaryStage.setHeight(525);
        primaryStage.setMinWidth(560);
        primaryStage.setMinHeight(510);
        primaryStage.setScene(scene);
        primaryStage.addEventFilter(WindowEvent.ANY, new StageEventHandler());
        primaryStage.show();
    }

    /**
     * Main start method.
     * @param args JVM args (not used for this application).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Self contained EventHandler for the primary stage that is used to manage the Application's Lifecycle.
     */
    private static class StageEventHandler implements EventHandler<WindowEvent> {

        @Override
        public void handle(WindowEvent windowEvent) {
            if (windowEvent.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)) {
                AppManager.getTimer().dispose();
            }

            if (windowEvent.getEventType().equals(WindowEvent.WINDOW_SHOWN)) {
                AppManager.getTimer().start();
            }
        }
    }
}
