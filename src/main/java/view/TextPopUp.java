package view;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

import view.utils.SceneController;

public class TextPopUp {
    @FXML
    private Label adviceText;

    private ActionEvent previousEvent;

    private SceneController sceneController;

    public void setAttributes(SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.sceneController = sceneController;
    }

    /** Utilizzato per impostare il testo nel pop-up in caso di riuso di questa classe per altri avvisi */
    public void setText(String string){
        adviceText.setText(string);
    }

    public void  setPreviousEvent(ActionEvent event){
        previousEvent = event;
    }
    @FXML
    private void closePopup(ActionEvent event) {
        // Chiudi il popup
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        // Devo fare goBack ma l'evento è avvenuto su uno stage diverso di quello del pop-up
        // Devo recuperare lo stage di partenza
        sceneController.goBack(previousEvent);
    }
}
