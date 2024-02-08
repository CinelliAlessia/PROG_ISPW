package view;

import controller.applicativo.AddPlaylistCtrlApplicativo;
import engineering.bean.*;

import engineering.exceptions.LinkIsNotValid;
import engineering.exceptions.PlaylistLinkAlreadyInUse;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import org.apache.commons.validator.routines.UrlValidator;
import view.utils.GenreManager;
import view.utils.MessageString;
import view.utils.SceneController;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddPlaylistCtrlGrafico implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField title;
    @FXML
    private TextField link;
    @FXML
    private CheckBox pop;
    @FXML
    private CheckBox indie;
    @FXML
    private CheckBox classic;
    @FXML
    private CheckBox rock;
    @FXML
    private CheckBox electronic;
    @FXML
    private CheckBox house;
    @FXML
    private CheckBox hipHop;
    @FXML
    private CheckBox jazz;
    @FXML
    private CheckBox acoustic;
    @FXML
    private CheckBox reb;
    @FXML
    private CheckBox country;
    @FXML
    private CheckBox alternative;
    private UserBean userBean;

    private List<CheckBox> checkBoxList;
    private SceneController sceneController;

    public void setAttributes(UserBean user, SceneController sceneController) {
        // Deve avere un userBean per compilare tutte le informazioni
        this.userBean = user;
        this.sceneController = sceneController;

        System.out.println("ADD_CG setUserBean: " + userBean);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxList = Arrays.asList(pop, indie, classic, rock, electronic, house, hipHop, jazz,
                acoustic, reb, country, alternative);
    }


    @FXML
    public void onBackClick(ActionEvent event) {
        sceneController.goBack(event);
    }

    /** Click sul tasto carica Playlist*/
    @FXML
    public void onUploadClick(ActionEvent event) {
        String linkPlaylist = link.getText();
        String titolo = title.getText();

        //Controllo sui campi vuoti

        if( !linkPlaylist.isEmpty() && !titolo.isEmpty() ){
            // Recupero generi della playlist
            List<String> genre = GenreManager.retrieveCheckList(checkBoxList);

            // La playlist è approvata solo se l'utente è un supervisore
            boolean approved = userBean.isSupervisor();

            // Costruzione della playlistBean con i parametri per il Controller Applicativo
            PlaylistBean playlistBean = null;
            try {
                playlistBean = new PlaylistBean(userBean.getEmail(), userBean.getUsername(), titolo, linkPlaylist, genre, approved);

                // Invocazione metodo controller Applicativo che in teoria è static
                AddPlaylistCtrlApplicativo addPlaylistControllerApplicativo = new AddPlaylistCtrlApplicativo();
                addPlaylistControllerApplicativo.insertPlaylist(playlistBean);

                if(approved){
                    sceneController.popUp(event, MessageString.ADDED_PLAYLIST);
                }
                else{
                    sceneController.popUp(event,MessageString.ADDED_PENDING_PLAYLIST);
                }
                System.out.println("PLAYLIST AGGIUNTA");

            } catch (PlaylistLinkAlreadyInUse | LinkIsNotValid e){
                showError(e.getMessage());
            }


        } else {
            // campi vuoti
            errorLabel.setText("I campi sono vuoti!");
            System.out.println("PLAYLIST NON AGGIUNTA");
        }
    }

    private void showError(String message) {
        // Mostra un messaggio di errore nell'interfaccia utente.
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
