// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.dialogs;

import edu.uncw.AppManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the restart.fxml dialog that appears when the user clicks on the 'restart' icon in the main UI.
 */
public class RestartController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button restartButton;

    @FXML
    void cancel(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void restart(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Stage stage = (Stage) restartButton.getScene().getWindow();
            stage.close();

            AppManager.newGame();
        }
    }

}
