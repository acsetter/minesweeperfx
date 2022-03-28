// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.dialogs;

import edu.uncw.AppManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the lost.fxml dialog that appears when the user loses the game in the main UI.
 */
public class LostController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button playButton;
    
    @FXML
    private Label timeSpent;
    
    @FXML
    private Label flagsUsed;
    
    @FXML
    private Label flagsLeft;

    @FXML
    void cancel(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void play(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.close();

            AppManager.newGame();
        }
    }
    
    public void initialize() {
    	timeSpent.setText(AppManager.getTime() + " seconds");
    	int remainingFlags = AppManager.getDifficulty().getMines() - Integer.parseInt(AppManager.getFlag());
    	int remainingFlags2 = Integer.parseInt(AppManager.getFlag());
    	flagsUsed.setText(String.valueOf(remainingFlags));
    	flagsLeft.setText(String.valueOf(remainingFlags2));
    }

}
