package view;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class SaveGenrePopUp {
    @FXML
    private Label adviceText;
    // Utilizzato per impostare il testo nel pop-up in caso di riuso di questa classe per altri avvisi
    public void setText(String string){
        adviceText.setText(string);
    }
    @FXML
    private void closePopup(ActionEvent event) {
        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
