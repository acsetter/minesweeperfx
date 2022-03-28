// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the help.fxml dialog that appears when the user clicks on the 'help' icon in the main UI.
 */
public class HelpController {
	@FXML
	private Button closeButton;

	@FXML
	void closeButtonClicked(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			Stage stage = (Stage) closeButton.getScene().getWindow(); // Fetch the dialog window.
			stage.close(); // Closes the dialog window.
		}
	}
}
