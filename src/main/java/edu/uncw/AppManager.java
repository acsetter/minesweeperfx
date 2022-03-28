package edu.uncw;
// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy

import edu.uncw.timer.Timer;
import edu.uncw.timer.TimerListener;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Manages the app state and various components used throughout the application.
 */
public final class AppManager {
    private static final Timer timer = new Timer(new GameTimerListener());
    private static final SimpleObjectProperty<Difficulty> difficulty = new SimpleObjectProperty<>(Difficulty.MEDIUM);
    private static final SimpleObjectProperty<Game> game = new SimpleObjectProperty<>();
    private static final SimpleStringProperty flag = new SimpleStringProperty();
    private static final SimpleStringProperty time = new SimpleStringProperty();

    /**
     * returns the Timer the app is using to keep track of time spent in game.
     * @return {@link Timer} object being used by the App.
     */
    public static Timer getTimer() {
        return timer;
    }

    /**
     * Returns the current {@link Difficulty} value of the game.
     * @return enum value of game {@link Difficulty}
     */
    public static Difficulty getDifficulty() {
        return difficulty.get();
    }

    /**
     * @return Property wrapper object for the {@link Difficulty} value.
     */
    public static SimpleObjectProperty<Difficulty> difficultyProperty() {
        return difficulty;
    }

    /**
     * Sets a new game {@link Difficulty}.
     * @param difficulty enum value of game {@link Difficulty}
     */
    public static void setDifficulty(Difficulty difficulty) {
        AppManager.difficulty.set(difficulty);
        newGame();
    }

    /**
     * @return Game object of the currently playable game.
     */
    public static Game getGame() {
        return gameProperty().get();
    }

    public static SimpleObjectProperty<Game> gameProperty() {
        return game;
    }

    /**
     * fetches time from the last timer update.
     * @return String representing time in seconds from timer's last update.
     */
    public static String getTime() {
        return time.get();
    }

    /**
     * Property binding a string of the time being updated by the time.
     * @return Property bound String representing time in seconds.
     */
    public static SimpleStringProperty timeProperty() {
        return time;
    }

    /**
     * <strong>NOTE: This should not be called anywhere but from the Timer's implemented listener class.</strong>
     * @param time String representing time in seconds.
     */
    private static void setTime(String time) {
        AppManager.time.set(time);
    }

    /**
     * Returns the count as a string of the flags unused by the player on the board.
     * @return String representing number of flags unused on the board.
     */
    public static String getFlag() {
        return flag.get();
    }

    /**
     * Property wrapper for the String of flags unused by the player on the board.
     * @return String representing number of flags unused on the board.
     */
    public static SimpleStringProperty flagProperty() {
        return flag;
    }

    /**
     * <strong>NOTE: This should not be called anywhere but from the game class.</strong>
     * @param flag String representing number of flags unused on the board.
     */
    public static void setFlag(String flag) {
        AppManager.flag.set(flag);
    }

    /**
     * Instantiates a new game.
     * @return new {@link Game} object that will be played and maount to the UI.
     */
    public static Game newGame() {
        gameProperty().set(new Game(getDifficulty()));
        getTimer().reset();
        return getGame();
    }

    /**
     * Invoked when user clicks the resetBtn.
     */
    public static void showResetDialog() {
        try {
            timer.pause(); // Pauses the timer in the main thread.
            Parent fxml = new FXMLLoader(AppManager.class.getResource("dialogs/restart.fxml")).load();
            Scene scene = new Scene(fxml, 300, 100);
            Stage stage = new Stage();
            stage.setTitle("Restart Game");
            stage.getIcons().add(new Image(AppManager.class.getResourceAsStream("AppIcon.png")));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Forces user to interact.
            stage.setResizable(false); // Disables fullscreen and window resize.
            stage.showAndWait(); // Pauses main thread until dialog closes.
            timer.resume(); // Resumes the timer in the main thread.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked when game instructions should be displayed.
     */
    public static void showHelpDialog() {
        try {
            Parent fxml = new FXMLLoader(AppManager.class.getResource("dialogs/help.fxml")).load();
            Scene scene = new Scene(fxml, 500, 600);
            Stage stage = new Stage();
            stage.setTitle("Help Page");
            stage.getIcons().add(new Image(AppManager.class.getResourceAsStream("AppIcon.png")));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked when the user won the game and a dialog should be displayed.
     */
    public static void showWinDialog() {
        try {
            timer.pause(); // Pauses the timer in the main thread.
            Parent fxml = new FXMLLoader(AppManager.class.getResource("dialogs/win.fxml")).load();
            Scene scene = new Scene(fxml);
            Stage stage = new Stage();
            stage.setTitle("You Win");
            stage.getIcons().add(new Image(AppManager.class.getResourceAsStream("AppIcon.png")));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Forces user to interact.
            stage.setResizable(false); // Disables fullscreen and window resize.
            stage.showAndWait(); // Pauses main thread until dialog closes.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Invoked when the user lost the game and a dialog should be displayed.
     */
    public static void showLossDialog() {
        try {
            timer.pause(); // Pauses the timer in the main thread.
            Parent fxml = new FXMLLoader(AppManager.class.getResource("dialogs/lost.fxml")).load();
            Scene scene = new Scene(fxml, 300, 166);
            Stage stage = new Stage();
            stage.setTitle("Game Over");
            stage.getIcons().add(new Image(AppManager.class.getResourceAsStream("AppIcon.png")));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Forces user to interact.
            stage.setResizable(false); // Disables fullscreen and window resize.
            stage.showAndWait(); // Pauses main thread until dialog closes.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class GameTimerListener implements TimerListener {

        @Override
        public void onStart(int seconds) {
            Platform.runLater(() -> setTime(String.valueOf(seconds)));
        }

        @Override
        public void onStop(int seconds) {}

        @Override
        public void onUpdate(int seconds) {
            Platform.runLater(() -> setTime(String.valueOf(seconds)));
        }
    }
}
