// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.dialogs;

import edu.uncw.AppManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the win.fxml dialog that appears when the user wins the game in the main UI.
 */
public class WinController {

    @FXML
    private Button exitButton;

    @FXML
    private Button playAgainButton;

    @FXML
    private Label timeLabel;

    @FXML
    private Label flagsLabel;

    @FXML
    void exit(MouseEvent event) {
    	 if (event.getButton().equals(MouseButton.PRIMARY)) {
             Stage stage = (Stage) exitButton.getScene().getWindow();
             stage.close();
         }
    }

    @FXML
    void restart(MouseEvent event) {
    	if (event.getButton().equals(MouseButton.PRIMARY)) {
            Stage stage = (Stage) playAgainButton.getScene().getWindow();
            stage.close();

            AppManager.newGame();
        }
    }
    
    public void initialize() {
    	timeLabel.setText(AppManager.getTime());
    	int totalFlags = AppManager.getDifficulty().getMines();
    	flagsLabel.setText(String.valueOf(totalFlags - Integer.parseInt(AppManager.getFlag())));
    }
    
}