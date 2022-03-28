package edu.uncw;
// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy

import edu.uncw.btns.IconBtn;
import edu.uncw.icons.Icon;
import edu.uncw.icons.IconType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller for the App.
 * <p>
 *     Gives static components built in the fxml functionality and mounts the gameBoard.
 * </p>
 */
public class AppController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ChoiceBox<Difficulty> difficultyChoiceBox;

    @FXML
    private Label flagLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private HBox btnHBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Updates the timeLabel via a listener of the AppManager's timeProperty:
        AppManager.timeProperty().addListener(
                (observableValue, oldStr, newStr) -> timeLabel.setText(newStr)
        );
        // Updates the flagLabel via a listener of the AppManager's flagProperty:
        AppManager.flagProperty().addListener(
                (observableValue, oldStr, newStr) -> flagLabel.setText(newStr)
        );
        // Updates the UI with a new game board when a new game is observed:
        AppManager.gameProperty().addListener(
                (observableValue, oldGame, newGame) -> borderPane.setCenter(AppManager.getGame().getGameBoard())
        );
        // Updates the size of the window when a new difficulty is observed:
        AppManager.difficultyProperty().addListener(
                (observableValue, difficulty, t1) -> handleStageSize()
        );
        // Define teh choiceBox's values and ties in select functionality:
        difficultyChoiceBox.getItems().addAll(Difficulty.values());
        difficultyChoiceBox.setValue(AppManager.getDifficulty());
        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldDifficulty, newDifficulty) -> AppManager.setDifficulty(newDifficulty));
        // Define the resetBtn to mount to the App's UI:
        IconBtn resetBtn = new IconBtn(new Icon(20, IconType.REDO_ALT)) {{
            setBtnHoverEffects(true);
            setCursor(Cursor.HAND);
            setIconHoverColor("#39c0db");
        }};
        // Define the helpBtn to mount to the App's UI:
        IconBtn helpBtn = new IconBtn(new Icon(20, IconType.QUESTION)) {{
            setBtnHoverEffects(true);
            setCursor(Cursor.HAND);
            setIconHoverColor("#39c0db");
        }};
        // Mounts the helpBtn and ResetBtn to the btnHBox in the App's UI:
        btnHBox.getChildren().addAll(helpBtn, resetBtn);
        // Add click functionality to resetBtn and helpBtn:
        resetBtn.setOnMouseClicked(mouseEvent -> AppManager.showResetDialog());
        helpBtn.setOnMouseClicked(mouseEvent -> AppManager.showHelpDialog());
        borderPane.setCenter(AppManager.newGame().getGameBoard());

    }

    /**
     * Alters the primaryStage's dimensions based on game difficulty.
     */
    private void handleStageSize() {
        Stage stage = App.getPrimaryStage();

        switch (AppManager.getDifficulty()) {
            case EASY:
            case DEMO:
                stage.setWidth(420);
                stage.setHeight(380);
                stage.setMinWidth(400);
                stage.setMinHeight(360);
                break;
            case HARD:
                stage.setWidth(760);
                stage.setHeight(700);
                stage.setMinWidth(740);
                stage.setMinHeight(690);
                break;
            case MEDIUM:
            default:
                stage.setWidth(580);
                stage.setHeight(525);
                stage.setMinWidth(560);
                stage.setMinHeight(510);
        }
    }
}

