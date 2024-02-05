package view;

import engineering.others.SceneController;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class SaveGenrePopUp {

    @FXML
    private void closePopup(ActionEvent event) {
        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        SceneController.getInstance().goBack(event);
    }
}
